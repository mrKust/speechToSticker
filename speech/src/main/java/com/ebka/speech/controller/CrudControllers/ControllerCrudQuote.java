package com.ebka.speech.controller.CrudControllers;

import com.ebka.speech.entity.Quote;
import com.ebka.speech.service.contracts.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quote")
public class ControllerCrudQuote {
    private QuoteService quoteService;

    public ControllerCrudQuote(QuoteService quoteService) {
        this.quoteService = quoteService;
    }
    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable String id){
        Quote quote = quoteService.getQuote(Integer.parseInt(id));
        if (quote != null){
            return ResponseEntity.ok(quote);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllGifs(){
        List<Quote> allQuotes = quoteService.getAllQuotes();
        if ( allQuotes!= null && !allQuotes.isEmpty()){
            return ResponseEntity.ok(allQuotes);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity create(@RequestBody Quote quote){
        boolean answer = quoteService.saveQuote(quote);
        if (answer){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id){
        quoteService.deleteQuote(Integer.parseInt(id));
        return ResponseEntity.ok().build();
    }
}
