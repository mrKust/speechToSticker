package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "quote_score")
    private int quoteScore;

    @ManyToMany
    @JoinTable(
            name = "gif_tags",
            joinColumns = @JoinColumn(name = "gifs_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    private List<Tags> tagsForGif;

    public Gif(String gifUrl, int quoteScore, List<Tags> tagsForGif) {
        this.gifUrl = gifUrl;
        this.quoteScore = quoteScore;
        this.tagsForGif = tagsForGif;
    }
}
