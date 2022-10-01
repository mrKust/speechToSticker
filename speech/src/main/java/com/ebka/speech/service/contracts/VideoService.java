package com.ebka.speech.service.contracts;

import com.ebka.speech.entity.Video;

import java.util.List;

public interface VideoService {

    public List<Video> getAllVideos();
    public boolean saveVideo(Video video);
    public Video getVideo(int id);
    public void deleteVideo(int id);
}
