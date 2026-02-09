package com.hms.AdvMedia.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.hms.AdvMedia.dto.MediaFileDTO;
import com.hms.AdvMedia.entity.MediaFile;

public interface MediaService {
    MediaFileDTO storeFile(MultipartFile file) throws IOException;
    Optional<MediaFile> getFile(Long id);
}
