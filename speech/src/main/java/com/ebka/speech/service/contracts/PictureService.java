package com.ebka.speech.service.contracts;

import com.ebka.speech.entity.Picture;

import java.util.List;

public interface PictureService {

    public List<Picture> getAllPictures();
    public boolean savePicture(Picture picture);
    public Picture getPicture(int id);
    public void deletePicture(int id);
}
