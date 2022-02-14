package com.springboot.springboot.Services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

//import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ImageStorageService implements IStorageService {

    //create object of path
    private final Path storageFolder = Paths.get("upload");

    public ImageStorageService() {// create folder to save image
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException("cannot instalize storage", e);
        }
    }

    public boolean isImageEmpty(MultipartFile file) {
        System.out.println(this.storageFolder);
        // get type file
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        // check type file have in array
        return Arrays.asList("png", "jpg").contains(fileExtension);
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file");
            }
            if (!isImageEmpty(file)) {
                throw new RuntimeException("You can only upload image file");
            }

            //check size file
            float fileSizeMergabytes = file.getSize() / 1_000_000.0f;
            if (fileSizeMergabytes > 5.0f) {
                throw new RuntimeException("file must be<=5MB");
            }

            // file must rename
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String genneratedFileName = UUID.randomUUID().toString().replace("-", "");
            genneratedFileName = genneratedFileName + "." + fileExtension;

            // vd:/upload
            // resolve giúp lien kết thành: /upload/abc.jpg
            // toAbsolutePath: trỏ tới ổ đỉa vd: D:/project/upload/abc.jpg
            Path destinationFilePath = this.storageFolder.resolve(
                    Paths.get(genneratedFileName)//abc.jps
            ).normalize().toAbsolutePath();

            Path path1 = destinationFilePath.getParent();// vd upload/abc.jpg => /upload
            Path path2 = this.storageFolder.toAbsolutePath();

            //getParent() return parent of the give file object
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory");
            }

            // copy file vừa gửi lên vào destinationFilePath
            //REPLACE_EXISTING nếu có tồn tại thì thay thế
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return genneratedFileName;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to store file", exception);
        }
    }

    @Override
    public Stream<Path> loadAll() {
//        Path p=Paths.get("upload/test");
        try {
            //get all file in folder
            return Files.walk(storageFolder, 1)
                    .filter(Files::isRegularFile);// delete path of the parent folder
//                    .map(storageFolder::relativize);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to load stores files", exception);
        }
    }

    @Override
    public Stream<Path> getOne(String fileName) throws IOException {
        try {
            return Files.walk(storageFolder).filter(Files::isRegularFile).filter(Files::isRegularFile)   // is a file
                    .filter(p -> p.getFileName().toString().equalsIgnoreCase(fileName));
        } catch (IOException exception) {
            throw new RuntimeException("Failed to load stores files", exception);
        }
    }


    @Override
    public byte[] readFileContent(String name) {
        try {
            // upload/name
            Path file = storageFolder.resolve(name);
            // find to resource contain file, and then pass url to uri
            // path file in project
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                // pass byte[]
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            } else {
                throw new RuntimeException("could not file" + name);
            }
        } catch (IOException exception) {
            throw new RuntimeException("could not file" + name + " " + exception);
        }
    }

    @Override
    public boolean deleteAllFile() {
        List<Path> result;
        //maxDepth chỉ lấy những file ở folder nay, không vào trong những folder con
        try (Stream<Path> walk = Files.walk(storageFolder, 1)) {
            result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
            result.stream().map(path -> {
                return deleteFile((path.getFileName().toString()));
            });
        } catch (IOException exception) {
            throw new RuntimeException("Failed delete files");
        }
        return true;
    }


    @Override
    public boolean deleteFile(String fileName) {
        Path pathFile = storageFolder.resolve(fileName).toAbsolutePath();
        try {
            return Files.deleteIfExists(pathFile);
        } catch (IOException exception) {
            throw new RuntimeException("Failed delete file" + fileName);
        }

    }

}
