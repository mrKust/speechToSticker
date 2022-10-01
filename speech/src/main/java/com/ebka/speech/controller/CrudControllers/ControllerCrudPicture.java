package com.ebka.speech.controller.CrudControllers;

import com.ebka.speech.entity.Gif;
import com.ebka.speech.entity.Picture;
import com.ebka.speech.service.contracts.PictureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/picture")
public class ControllerCrudPicture {
    private PictureService pictureService;

    public ControllerCrudPicture(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable String id){
        Picture picture = pictureService.getPicture(Integer.parseInt(id));
        if (picture != null){
            return ResponseEntity.ok(picture);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllGifs(){
        List<Picture> allPictures = pictureService.getAllPictures();
        if ( allPictures!= null && !allPictures.isEmpty()){
            return ResponseEntity.ok(allPictures);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody Picture picture){
        boolean answer = pictureService.savePicture(picture);
        if (answer){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id){
        pictureService.deletePicture(Integer.parseInt(id));
        return ResponseEntity.ok().build();
    }
}
