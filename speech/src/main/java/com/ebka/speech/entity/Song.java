package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Song")
@Getter
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "song_url")
    private String songUrl;

    @Column(name = "song_score")
    private int quoteScore;

    @ManyToMany
    @JoinTable(
            name = "song_tags",
            joinColumns = @JoinColumn(name = "songs_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    private List<Tags> tagsForSong;

    public Song(String songUrl, int quoteScore, List<Tags> tagsForSong) {
        this.songUrl = songUrl;
        this.quoteScore = quoteScore;
        this.tagsForSong = tagsForSong;
    }
}
