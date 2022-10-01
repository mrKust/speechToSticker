package com.ebka.speech.service.impl;

import com.ebka.speech.dao.PictureRepository;
import com.ebka.speech.entity.Picture;
import com.ebka.speech.service.contracts.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {

    private PictureRepository pictureDAO;

    public PictureServiceImpl(PictureRepository pictureDAO) {
        this.pictureDAO = pictureDAO;
    }

    @Override
    public List<Picture> getAllPictures() {
        return pictureDAO.findAll();
    }

    @Override
    public void savePicture(Picture picture) {
        pictureDAO.save(picture);
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
