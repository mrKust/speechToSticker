package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    private String gifUrl;

    @Column(name = "video_score")
    private int quoteScore;

    @ManyToMany
    @JoinTable(
            name = "video_tags",
            joinColumns = @JoinColumn(name = "videos_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    private List<Tags> tagsForVideo;

    public Video(String gifUrl, int quoteScore, List<Tags> tagsForVideo) {
        this.gifUrl = gifUrl;
        this.quoteScore = quoteScore;
        this.tagsForVideo = tagsForVideo;
    }
}
