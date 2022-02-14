package com.springboot.springboot.Controllers;

import com.springboot.springboot.Models.ResponseObject;
import com.springboot.springboot.Services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/api/v1/upload")
public class FileUploadController {

    @Autowired
    private IStorageService iStorageService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            // save file to a folder =>use service
            String generateFileName = iStorageService.storeFile(multipartFile);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "update file success", generateFileName));

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("400", exception.getMessage(), ""));

        }
    }

    @GetMapping("/file/{fileName}")
    ResponseEntity<byte[]> ReadFileimage(@PathVariable String fileName) {
        try {
            byte[] bytes = iStorageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/path/{fileName}")
    ResponseEntity<ResponseObject> getFilePath(@PathVariable String fileName) {
        try {
          Stream<URI> url = iStorageService.getOne(fileName)
                    .map(path -> MvcUriComponentsBuilder
                            .fromMethodName(FileUploadController.class, "ReadFileimage", path.getFileName().toString())
                            .build().toUri());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "List file success", url));
        } catch (IOException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseObject("400", "List file success", ""));
        }
    }

    @GetMapping("list")
    ResponseEntity<ResponseObject> getListFile() {
        try {
            List<String> urls = iStorageService.loadAll()
                    .map(path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "ReadFileimage", path.getFileName().toString())
                            .build().toUri().toString()
                    ).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "List file success", urls));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseObject("400", "List file success", ""));
        }
    }
    @DeleteMapping("file/{fileName}")
    ResponseEntity<ResponseObject> deleteFile(@PathVariable String fileName)
    {
        try {
            boolean result =iStorageService.deleteFile(fileName);
            return result? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200","Delete file success",true))
                    :ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("400","Cannot find file",false));
        }catch (Exception exception)
        {
            return  ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("400","Failed delete file",exception));
        }
    }
    @DeleteMapping("deleteFiles")
    ResponseEntity<ResponseObject> deleteFiles()
    {
        boolean result=iStorageService.deleteAllFile();
        return  ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("400","Failed delete file",""));
    }
}
