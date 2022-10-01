package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Poety")
@Getter
@NoArgsConstructor
public class Poety {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    private String text;

    @Column(name = "author")
    private String author;

    @Column(name = "poety_score")
    private int poetyScore;

    @ManyToMany
    @JoinTable(
            name = "poety_tags",
            joinColumns = @JoinColumn(name = "poetys_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    private List<Tags> tagsForPoety;

    public Poety(String text, String author, int poetyScore, List<Tags> tagsForPoety) {
        this.text = text;
        this.author = author;
        this.poetyScore = poetyScore;
        this.tagsForPoety = tagsForPoety;
    }
}
