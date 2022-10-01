package com.ebka.speech.service.contracts;

import com.ebka.speech.entity.Poety;

import java.util.List;

public interface PoetyService {

    public List<Poety> getAllPoety();
    public void savePoety(Poety poety);
    public Poety getPoety(int id);
    public void deletePoety(int id);
}
