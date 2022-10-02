package com.ebka.speech.service.impl;

import com.ebka.speech.dao.QuoteRepository;
import com.ebka.speech.dao.TagsRepository;
import com.ebka.speech.entity.Quote;
import com.ebka.speech.entity.Tags;
import com.ebka.speech.service.contracts.QuoteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuoteServiceImpl implements QuoteService {

    private QuoteRepository quoteDAO;
    private TagsRepository tagsDAO;

    public QuoteServiceImpl(QuoteRepository quoteDAO, TagsRepository tagsDAO) {
        this.quoteDAO = quoteDAO;
        this.tagsDAO = tagsDAO;
    }

    @Override
    public List<Quote> getAllQuotes() {
        return quoteDAO.findAll();
    }

    @Override
    public boolean saveQuote(Quote quote) {
        Quote save = quoteDAO.save(quote);
        if (save != null){
            Optional<Tags> byId = tagsDAO.findById(save.getTagName());
            if (byId.isPresent()){
                Tags tags = byId.get();
                String idQuote = tags.getIdQuote();
                if (idQuote == null){
                    idQuote = ""+save.getId();
                }else{
                    idQuote += ","+save.getId();
                }
                tags.setIdQuote(idQuote);
                tagsDAO.save(tags);
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Quote getQuote(int id) {
        Optional<Quote> result = quoteDAO.findById(id);
        if (result.isPresent())
            return result.get();
        else return null;
    }

    @Override
    public void deleteQuote(int id) {
        quoteDAO.deleteById(id);
    }
}
