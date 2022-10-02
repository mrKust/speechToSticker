package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Gif")
@Getter
@NoArgsConstructor
public class Gif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "gif_url")
    private String gifUrl;

    @Column(name = "tag_name")
    private String tagName;

    public Gif(String gifUrl, String tagName) {
        this.gifUrl = gifUrl;
        this.tagName = tagName;
    }
}
