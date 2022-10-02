package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Picture")
@Getter
@NoArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "picture_url")
    private String pictureURL;

    @Column(name = "tag_name")
    private String tagName;

    public Picture(String pictureURL, String tagName) {
        this.pictureURL = pictureURL;
        this.tagName = tagName;
    }
}
