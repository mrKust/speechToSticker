package com.ebka.speech.dao;

import com.ebka.speech.entity.Poety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoetyRepository extends JpaRepository<Poety, Integer> {
}
