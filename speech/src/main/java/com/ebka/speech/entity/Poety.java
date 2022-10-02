package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "tag_name")
    private String tagName;

    public Poety(String text, String author, String tagName) {
        this.text = text;
        this.author = author;
        this.tagName = tagName;
    }
}
