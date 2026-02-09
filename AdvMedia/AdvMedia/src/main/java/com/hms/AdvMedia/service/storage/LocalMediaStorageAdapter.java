package com.hms.AdvMedia.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.hms.AdvMedia.entity.MediaFile;
import com.hms.AdvMedia.repository.MediaFileRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocalMediaStorageAdapter implements MediaStorageAdapter {

    private static final String BASE_PATH = "/var/media/";

    private final MediaFileRepository repository;

    @Override
    public MediaFile save(MediaFile mediaFile) {

        try {
            Path path = Paths.get(BASE_PATH + mediaFile.getName());
            Files.write(path, mediaFile.getData());
        } catch (IOException e) {
            throw new RuntimeException("Local storage failed", e);
        }

        // DB keeps metadata only
        mediaFile.setData(null);

        return repository.save(mediaFile);
    }

    @Override
    public Optional<MediaFile> get(Long id) {
        return repository.findById(id);
    }
}
