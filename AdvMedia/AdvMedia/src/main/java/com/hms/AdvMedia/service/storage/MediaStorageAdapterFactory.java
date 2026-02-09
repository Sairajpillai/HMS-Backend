package com.hms.AdvMedia.service.storage;

import org.springframework.stereotype.Component;

import com.hms.AdvMedia.entity.Storage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MediaStorageAdapterFactory {

    private final DbMediaStorageAdapter dbAdapter;
    private final S3MediaStorageAdapter s3Adapter;
    private final LocalMediaStorageAdapter localAdapter;

    public MediaStorageAdapter getAdapter(Storage storage) {
        return switch (storage) {
            case DB -> dbAdapter;
            case S3 -> s3Adapter;
            case LOCAL -> localAdapter;
        };
    }
}

