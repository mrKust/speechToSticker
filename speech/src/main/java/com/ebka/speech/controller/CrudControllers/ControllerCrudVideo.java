package com.ebka.speech.controller.CrudControllers;

import com.ebka.speech.entity.Video;
import com.ebka.speech.service.contracts.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/video")
public class ControllerCrudVideo {
    private VideoService videoService;

    public ControllerCrudVideo(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable String id){
        Video video = videoService.getVideo(Integer.parseInt(id));
        if (video != null){
            return ResponseEntity.ok(video);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllGifs(){
        List<Video> allVideos = videoService.getAllVideos();
        if ( allVideos!= null && !allVideos.isEmpty()){
            return ResponseEntity.ok(allVideos);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody Video video){
        boolean answer = videoService.saveVideo(video);
        if (answer){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping( "/delete/{id}")
    public ResponseEntity delete(@PathVariable String id){
        videoService.deleteVideo(Integer.parseInt(id));
        return ResponseEntity.ok().build();
    }
}
