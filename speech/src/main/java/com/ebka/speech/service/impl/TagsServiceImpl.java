package com.ebka.speech.service.impl;

import com.ebka.speech.dao.TagsRepository;
import com.ebka.speech.entity.Tags;
import com.ebka.speech.service.contracts.TagsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagsServiceImpl implements TagsService {

    private TagsRepository tagsDAO;

    public TagsServiceImpl(TagsRepository tagsDAO) {
        this.tagsDAO = tagsDAO;
    }

    @Override
    public List<Tags> getAllTags() {
        return tagsDAO.findAll();
    }

    @Override
    public void saveTags(Tags tags) {
        tagsDAO.save(tags);
    }

    @Override
    public Tags getTags(int id) {
        Optional<Tags> result = tagsDAO.findById(id);
        if (result.isPresent())
            return result.get();
        else return null;
    }

    @Override
    public void deleteTags(int id) {
        tagsDAO.deleteById(id);
    }
}
