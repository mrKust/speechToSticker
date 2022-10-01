package com.ebka.speech.controller.CrudControllers;


import com.ebka.speech.entity.Poety;
import com.ebka.speech.service.contracts.PoetyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/poety")
public class ControllerCrudPoety {
    private PoetyService poetyService;

    public ControllerCrudPoety(PoetyService poetyService) {
        this.poetyService = poetyService;
    }
    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Integer id){
        System.out.println(id);
        Poety poety = poetyService.getPoety(id);
        if (poety != null){
            return ResponseEntity.ok(poety);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllGifs(){
        List<Poety> allPoety = poetyService.getAllPoety();
        if ( allPoety!= null && !allPoety.isEmpty()){
            return ResponseEntity.ok(allPoety);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody Poety poety){
        boolean answer = poetyService.savePoety(poety);
        if (answer){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id){
        poetyService.deletePoety(Integer.parseInt(id));
        return ResponseEntity.ok().build();
    }
}
