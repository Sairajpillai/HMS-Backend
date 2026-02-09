package com.hms.AdvMedia.service.storage;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.hms.AdvMedia.entity.MediaFile;
import com.hms.AdvMedia.repository.MediaFileRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3MediaStorageAdapter implements MediaStorageAdapter {

    private final MediaFileRepository repository;

    @Override
    public MediaFile save(MediaFile mediaFile) {

        // TODO: upload to S3 using AWS SDK
        // s3Client.putObject(bucket, key, mediaFile.getData());

        // DB should not store bytes
        mediaFile.setData(null);

        return repository.save(mediaFile);
    }

    @Override
    public Optional<MediaFile> get(Long id) {
        return repository.findById(id);
    }
}
