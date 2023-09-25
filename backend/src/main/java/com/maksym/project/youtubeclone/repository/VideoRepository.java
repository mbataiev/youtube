package com.maksym.project.youtubeclone.repository;

import com.maksym.project.youtubeclone.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}

