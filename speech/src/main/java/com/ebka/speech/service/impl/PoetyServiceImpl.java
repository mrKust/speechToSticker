package com.ebka.speech.service.impl;

import com.ebka.speech.dao.PoetyRepository;
import com.ebka.speech.dao.TagsRepository;
import com.ebka.speech.entity.Poety;
import com.ebka.speech.entity.Tags;
import com.ebka.speech.service.contracts.PoetyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PoetyServiceImpl implements PoetyService {

    private PoetyRepository poetyDAO;
    private TagsRepository tagsDAO;

    public PoetyServiceImpl(PoetyRepository poetyDAO, TagsRepository tagsDAO) {
        this.poetyDAO = poetyDAO;
        this.tagsDAO = tagsDAO;
    }

    @Override
    public List<Poety> getAllPoety() {
        return poetyDAO.findAll();
    }

    @Override
    public boolean savePoety(Poety poety) {
        Poety save = poetyDAO.save(poety);
        if (save != null){
            Optional<Tags> byId = tagsDAO.findById(save.getTagName());
            if (byId.isPresent()){
                Tags tags = byId.get();
                String idPoety = tags.getIdPoety();
                if (idPoety == null){
                    idPoety = ""+save.getId();
                }else{
                    idPoety += ","+save.getId();
                }
                tags.setIdPoety(idPoety);
                tagsDAO.save(tags);
            }
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
