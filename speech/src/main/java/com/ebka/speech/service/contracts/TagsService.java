package com.ebka.speech.service.contracts;


import com.ebka.speech.entity.Tags;

import java.util.List;

public interface TagsService {

    public List<Tags> getAllTags();
    public boolean saveTags(Tags tags);
    public Tags getTags(int id);
    public void deleteTags(int id);
}
