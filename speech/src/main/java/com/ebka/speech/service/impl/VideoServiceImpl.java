package com.ebka.speech.service.impl;

import com.ebka.speech.dao.TagsRepository;
import com.ebka.speech.dao.VideoRepository;
import com.ebka.speech.entity.Tags;
import com.ebka.speech.entity.Video;
import com.ebka.speech.service.contracts.VideoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    private VideoRepository videoDAO;
    private TagsRepository tagsDAO;

    public VideoServiceImpl(VideoRepository videoDAO, TagsRepository tagsDAO) {
        this.videoDAO = videoDAO;
        this.tagsDAO = tagsDAO;
    }

    @Override
    public List<Video> getAllVideos() {
        return videoDAO.findAll();
    }

    @Override
    public boolean saveVideo(Video video) {
        Optional<Tags> byId = tagsDAO.findById(video.getTagName());
        if (byId.isPresent()){
            Tags tags = byId.get();
            String idVideo = tags.getIdVideo();
            idVideo += ","+video.getId();
            tags.setIdVideo(idVideo);
            tagsDAO.save(tags);
        }
        Video save = videoDAO.save(video);
        if (save != null){
            return true;
        } else{
            return false;
        }
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
