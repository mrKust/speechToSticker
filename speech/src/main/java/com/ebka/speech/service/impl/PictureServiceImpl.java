package com.ebka.speech.service.impl;

import com.ebka.speech.dao.PictureRepository;
import com.ebka.speech.dao.TagsRepository;
import com.ebka.speech.entity.Picture;
import com.ebka.speech.entity.Tags;
import com.ebka.speech.service.contracts.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {

    private PictureRepository pictureDAO;
    private TagsRepository tagsDAO;

    public PictureServiceImpl(PictureRepository pictureDAO, TagsRepository tagsDAO) {
        this.pictureDAO = pictureDAO;
        this.tagsDAO = tagsDAO;
    }

    @Override
    public List<Picture> getAllPictures() {
        return pictureDAO.findAll();
    }

    @Override
    public boolean savePicture(Picture picture) {
        Picture save = pictureDAO.save(picture);
        if (save != null){
            Optional<Tags> byId = tagsDAO.findById(save.getTagName());
            if (byId.isPresent()){
                Tags tags = byId.get();
                String idPic = tags.getIdPic();
                if (idPic == null){
                    idPic = ""+save.getId();
                }else{
                    idPic += ","+save.getId();
                }
                tags.setIdPic(idPic);
                tagsDAO.save(tags);
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Picture getPicture(int id) {
        Optional<Picture> result = pictureDAO.findById(id);
        if (result.isPresent())
            return result.get();
        else return null;
    }

    @Override
    public void deletePicture(int id) {
        pictureDAO.deleteById(id);
    }
}
