package com.ebka.speech.service.impl;

import com.ebka.speech.dao.QuoteRepository;
import com.ebka.speech.entity.Quote;
import com.ebka.speech.service.contracts.QuoteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuoteServiceImpl implements QuoteService {

    private QuoteRepository quoteDAO;

    public QuoteServiceImpl(QuoteRepository quoteDAO) {
        this.quoteDAO = quoteDAO;
    }

    @Override
    public List<Quote> getAllQuotes() {
        return quoteDAO.findAll();
    }

    @Override
    public void saveQuote(Quote quote) {
        quoteDAO.save(quote);
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
