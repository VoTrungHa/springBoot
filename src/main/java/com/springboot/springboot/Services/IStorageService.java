package com.springboot.springboot.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface IStorageService {
    String storeFile(MultipartFile file);

    Stream<Path> loadAll() throws IOException;
    Stream<Path> getOne(String fileName) throws IOException;
    byte[] readFileContent(String name);
    boolean deleteAllFile();
    boolean deleteFile(String fileName) ;
}
