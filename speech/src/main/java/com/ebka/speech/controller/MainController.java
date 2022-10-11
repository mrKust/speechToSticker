package com.ebka.speech.controller;

import com.ebka.speech.dto.PairDTO;
import com.ebka.speech.service.contracts.MainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {

    private MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping("/getReaction")
    public ResponseEntity getOutputData(@RequestBody PairDTO inputData) {
        PairDTO result = mainService.getEmotion(inputData);
        if (result !=null)
            return ResponseEntity.ok(result);
        else return ResponseEntity.noContent().build();
    }

}