package com.ebka.speech.controller.CrudControllers;

import com.ebka.speech.entity.Gif;
import com.ebka.speech.service.contracts.GifService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gif")
public class ControllerCrudGif {
    private GifService gifService;

    public ControllerCrudGif(GifService gifService) {
        this.gifService = gifService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable String id){
        Gif gif = gifService.getGif(Integer.parseInt(id));
        if (gif != null){
            return ResponseEntity.ok(gif);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllGifs(){
        List<Gif> allGifs = gifService.getAllGifs();
        if ( allGifs!= null && !allGifs.isEmpty()){
            return ResponseEntity.ok(allGifs);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody Gif gif){
        boolean answer = gifService.saveGif(gif);
        if (answer){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id){
        gifService.deleteGif(Integer.parseInt(id));
        return ResponseEntity.ok().build();
    }
}
