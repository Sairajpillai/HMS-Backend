package com.hms.AdvMedia.utility;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.hms.AdvMedia.entity.MediaFile;
import com.hms.AdvMedia.entity.Storage;

@Component
public class MediaFileFactory {

    private static final long DB_LIMIT = 2 * 1024 * 1024;     // 2 MB
    private static final long S3_LIMIT = 5 * 1024 * 1024;     // 5 MB

    public MediaFile fromMultipart(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        Storage storage = decideStorage(file.getSize());

        return MediaFile.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .data(file.getBytes())   // adapters will decide what to do with data
                .storage(storage)
                .build();
    }

    private Storage decideStorage(long size) {
        if (size <= DB_LIMIT) {
            return Storage.DB;
        } else if (size <= S3_LIMIT) {
            return Storage.S3;
        } else {
            return Storage.LOCAL;
        }
    }
}

