package com.ebka.speech.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordWeightRepository extends JpaRepository<WordWeight, Integer> {
}
