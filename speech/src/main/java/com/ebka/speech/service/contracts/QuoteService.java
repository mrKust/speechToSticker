package com.ebka.speech.service.contracts;


import com.ebka.speech.entity.Quote;

import java.util.List;

public interface QuoteService {

    public List<Quote> getAllQuotes();
    public boolean saveQuote(Quote quote);
    public Quote getQuote(int id);
    public void deleteQuote(int id);
}
