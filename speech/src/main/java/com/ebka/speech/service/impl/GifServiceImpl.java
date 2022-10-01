package com.ebka.speech.service.impl;

import com.ebka.speech.dao.GifRepository;
import com.ebka.speech.entity.Gif;
import com.ebka.speech.service.contracts.GifService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GifServiceImpl implements GifService {

    private GifRepository gifDAO;

    public GifServiceImpl(GifRepository gifDAO) {
        this.gifDAO = gifDAO;
    }

    @Override
    public List<Gif> getAllGifs() {
        return gifDAO.findAll();
    }

    @Override
    public boolean saveGif(Gif gif) {
        Gif gifAnswer = gifDAO.save(gif);
        if (gifAnswer != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Gif getGif(int id) {
        Optional<Gif> result = gifDAO.findById(id);
        if (result.isPresent())
            return result.get();
        else return null;
    }

    @Override
    public void deleteGif(int id) {
        gifDAO.deleteById(id);
    }
}
