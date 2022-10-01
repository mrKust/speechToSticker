package com.ebka.speech.service.impl;

import com.ebka.speech.dao.SongRepository;
import com.ebka.speech.entity.Song;
import com.ebka.speech.service.contracts.SongService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private SongRepository songDAO;

    public SongServiceImpl(SongRepository songDAO) {
        this.songDAO = songDAO;
    }

    @Override
    public List<Song> getAllSongs() {
        return songDAO.findAll();
    }

    @Override
    public void saveSong(Song song) {
        songDAO.save(song);
    }

    @Override
    public Song getSong(int id) {
        Optional<Song> result = songDAO.findById(id);
        if (result.isPresent())
            return result.get();
        else return null;
    }

    @Override
    public void deleteSong(int id) {
        songDAO.deleteById(id);
    }
}
