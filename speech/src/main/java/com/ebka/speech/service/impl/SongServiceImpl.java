package com.ebka.speech.service.impl;

import com.ebka.speech.dao.SongRepository;
import com.ebka.speech.dao.TagsRepository;
import com.ebka.speech.entity.Song;
import com.ebka.speech.entity.Tags;
import com.ebka.speech.service.contracts.SongService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private SongRepository songDAO;
    private TagsRepository tagsDAO;

    public SongServiceImpl(SongRepository songDAO, TagsRepository tagsDAO) {
        this.songDAO = songDAO;
        this.tagsDAO = tagsDAO;
    }

    @Override
    public List<Song> getAllSongs() {
        return songDAO.findAll();
    }

    @Override
    public boolean saveSong(Song song) {
        Optional<Tags> byId = tagsDAO.findById(song.getTagName());
        if (byId.isPresent()){
            Tags tags = byId.get();
            String idSong = tags.getIdSong();
            idSong += ","+song.getId();
            tags.setIdSong(idSong);
            tagsDAO.save(tags);
        }
        Song save = songDAO.save(song);
        if (save != null){
            return true;
        }else{
            return false;
        }
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
