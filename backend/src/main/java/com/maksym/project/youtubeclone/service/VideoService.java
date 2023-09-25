package com.maksym.project.youtubeclone.service;

import com.maksym.project.youtubeclone.dto.UploadVideoResponse;
import com.maksym.project.youtubeclone.dto.VideoDto;
import com.maksym.project.youtubeclone.model.Video;
import com.maksym.project.youtubeclone.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
        String videoUrl = s3Service.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        var savedVideo = videoRepository.save(video);
        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getThumbnailUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        var savedVideo = getVideoById(videoDto.getId());

        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());

        videoRepository.save(savedVideo);

        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        var savedVideo = getVideoById(videoId);

        String thumbnailUrl = s3Service.uploadFile(file);
        savedVideo.setThumbnailUrl(thumbnailUrl);

        videoRepository.save(savedVideo);

        return thumbnailUrl;
    }

    private Video getVideoById(String videoId) {
        return videoRepository.findById(videoId).orElseThrow(
                () -> new IllegalArgumentException(String.format("Cannot find video by id -> %s", videoId))
        );
    }
}
