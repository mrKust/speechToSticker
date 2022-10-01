package com.ebka.speech.controller.CrudControllers;


import com.ebka.speech.entity.Song;
import com.ebka.speech.service.contracts.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
public class ControllerCrudSong {
    private SongService songService;

    public ControllerCrudSong(SongService songService) {
        this.songService = songService;
    }
    @GetMapping("/{id}")
    public ResponseEntity getOne(@RequestBody String id){
        Song song = songService.getSong(Integer.parseInt(id));
        if (song != null){
            return ResponseEntity.ok(song);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllGifs(){
        List<Song> allSongs = songService.getAllSongs();
        if ( allSongs!= null && !allSongs.isEmpty()){
            return ResponseEntity.ok(allSongs);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody Song song){
        boolean answer = songService.saveSong(song);
        if (answer){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id]")
    public ResponseEntity delete(@RequestBody String id){
        songService.deleteSong(Integer.parseInt(id));
        return ResponseEntity.ok().build();
    }
}
