package com.hms.AdvMedia.service.storage;

import java.util.Optional;

import com.hms.AdvMedia.entity.MediaFile;

public interface MediaStorageAdapter {

    MediaFile save(MediaFile mediaFile);

    Optional<MediaFile> get(Long id);
}

