package com.ebka.speech.service.impl;

import com.ebka.speech.dao.PoetyRepository;
import com.ebka.speech.entity.Poety;
import com.ebka.speech.service.contracts.PoetyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PoetyServiceImpl implements PoetyService {

    private PoetyRepository poetyDAO;

    public PoetyServiceImpl(PoetyRepository poetyDAO) {
        this.poetyDAO = poetyDAO;
    }

    @Override
    public List<Poety> getAllPoety() {
        return getAllPoety();
    }

    @Override
    public boolean savePoety(Poety poety) {
        Poety save = poetyDAO.save(poety);
        if (save != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Poety getPoety(int id) {
        Optional<Poety> result = poetyDAO.findById(id);
        if (result.isPresent())
            return result.get();
        else return null;
    }

    @Override
    public void deletePoety(int id) {
        poetyDAO.deleteById(id);
    }
}
