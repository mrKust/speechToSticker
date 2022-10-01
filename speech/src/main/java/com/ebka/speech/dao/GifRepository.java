package com.ebka.speech.dao;

import com.ebka.speech.entity.Gif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GifRepository extends JpaRepository<Gif, Integer> {
}
