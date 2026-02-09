package com.hms.AdvMedia.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hms.AdvMedia.dto.MediaFileDTO;
import com.hms.AdvMedia.entity.MediaFile;
import com.hms.AdvMedia.entity.Storage;
import com.hms.AdvMedia.repository.MediaFileRepository;
import com.hms.AdvMedia.service.storage.MediaStorageAdapter;
import com.hms.AdvMedia.service.storage.MediaStorageAdapterFactory;
import com.hms.AdvMedia.utility.MediaFileFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaFileFactory mediaFileFactory;
    private final MediaStorageAdapterFactory adapterFactory;

    @Override
    public MediaFileDTO storeFile(MultipartFile file) throws IOException {

        // 1️⃣ Build entity (Builder + rules)
        MediaFile mediaFile = mediaFileFactory.fromMultipart(file);

        // 2️⃣ Choose adapter based on decided storage
        MediaStorageAdapter adapter =
                adapterFactory.getAdapter(mediaFile.getStorage());

        // 3️⃣ Save using adapter
        MediaFile savedFile = adapter.save(mediaFile);

        // 4️⃣ Return DTO
        return MediaFileDTO.builder()
                .id(savedFile.getId())
                .name(savedFile.getName())
                .type(savedFile.getType())
                .size(savedFile.getSize())
                .build();
    }

    @Override
    public Optional<MediaFile> getFile(Long id) {
        return adapterFactory
                .getAdapter(Storage.DB)
                .get(id);
    }
}


// @Service
// @RequiredArgsConstructor
// public class MediaServiceImpl implements MediaService {

//     private final MediaFileRepository mediaRepository;

//     @Override
//     public MediaFileDTO storeFile(MultipartFile file) throws IOException {
//         MediaFile mediaFile = MediaFile.builder()
//         .name(file.getOriginalFilename())
//         .type(file.getContentType())
//         .size(file.getSize())
//         .data(file.getBytes())
//         .storage(Storage.DB)
//         .build();

//         MediaFile savedFile = mediaRepository.save(mediaFile);
//         return MediaFileDTO.builder()
//                 .id(savedFile.getId())
//                 .name(savedFile.getName())
//                 .type(savedFile.getType())
//                 .size(savedFile.getSize())
//                 .build();

//     }

//     @Override
//     public Optional<MediaFile> getFile(Long id) {
//         return mediaRepository.findById(id);
//     }

// }
