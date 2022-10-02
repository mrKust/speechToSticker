package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Video")
@Getter
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "song_url")
    private String songUrl;

    @Column(name = "tag_name")
    private String tagName;

    public Video(String songUrl, String tagName) {
        this.songUrl = songUrl;
        this.tagName = tagName;
    }
}
