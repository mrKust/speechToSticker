package com.ebka.speech.service.contracts;


import com.ebka.speech.entity.Song;

import java.util.List;

public interface SongService {

    public List<Song> getAllSongs();
    public void saveSong(Song song);
    public Song getSong(int id);
    public void deleteSong(int id);
}
