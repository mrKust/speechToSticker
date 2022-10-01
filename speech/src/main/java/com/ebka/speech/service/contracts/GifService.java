package com.ebka.speech.service.contracts;


import com.ebka.speech.entity.Gif;

import java.util.List;

public interface GifService {

    public List<Gif> getAllGifs();
    public void saveGif(Gif gif);
    public Gif getGif(int id);
    public void deleteGif(int id);
}
