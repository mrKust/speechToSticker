package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "WordWeight")
@NoArgsConstructor
@Getter
public class WordWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "word")
    private String word;

    @Column(name = "weight")
    private int weight;

    public WordWeight(String word, int weight) {
        this.word = word;
        this.weight = weight;
    }
}
