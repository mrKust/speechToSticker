package com.ebka.speech.service.impl;

import com.ebka.speech.dao.VideoRepository;
import com.ebka.speech.entity.Video;
import com.ebka.speech.service.contracts.VideoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    private VideoRepository videoDAO;

    public VideoServiceImpl(VideoRepository videoDAO) {
        this.videoDAO = videoDAO;
    }

    @Override
    public List<Video> getAllVideos() {
        return videoDAO.findAll();
    }

    @Override
    public void saveVideo(Video video) {
        videoDAO.save(video);
    }

    @Override
    public Video getVideo(int id) {
        Optional<Video> result = videoDAO.findById(id);
        if (result.isPresent())
            return result.get();
        else return null;
    }

    @Override
    public void deleteVideo(int id) {
        videoDAO.deleteById(id);
    }
}
