package com.ebka.speech.controller.CrudControllers;


import com.ebka.speech.entity.Tags;
import com.ebka.speech.service.contracts.TagsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class ControllerCrudTags {
    private TagsService tagsService;

    public ControllerCrudTags(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable String id){
        Tags tags = tagsService.getTags(Integer.parseInt(id));
        if (tags != null){
            return ResponseEntity.ok(tags);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllGifs(){
        List<Tags> allTags = tagsService.getAllTags();
        if ( allTags!= null && !allTags.isEmpty()){
            return ResponseEntity.ok(allTags);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody Tags tags){
        boolean answer = tagsService.saveTags(tags);
        if (answer){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id){
        tagsService.deleteTags(Integer.parseInt(id));
        return ResponseEntity.ok().build();
    }
}
