package com.hms.AdvMedia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hms.AdvMedia.entity.MediaFile;

@Repository
public interface MediaFileRepository extends CrudRepository<MediaFile,Long>{
    
}
