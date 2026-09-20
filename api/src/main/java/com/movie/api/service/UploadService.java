package com.movie.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    //Validate and save a file, returning its Upload ID
    String checkAndSaveUpload(MultipartFile file);

    void deleteById(String id);

}
