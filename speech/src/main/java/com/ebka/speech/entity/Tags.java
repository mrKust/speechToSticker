package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tags")
@Getter
@NoArgsConstructor
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "tag_score")
    private int tagScore;

    @ManyToMany
    @JoinTable(
            name = "picture_tags",
            joinColumns = @JoinColumn(name = "tags_id"),
            inverseJoinColumns = @JoinColumn(name = "pictures_id")
    )
    private List<Picture> picturesWithThisTags;

    @ManyToMany
    @JoinTable(
            name = "quote_tags",
            joinColumns = @JoinColumn(name = "tags_id"),
            inverseJoinColumns = @JoinColumn(name = "quotes_id")
    )
    private List<Picture> quotesWithThisTags;

    @ManyToMany
    @JoinTable(
            name = "poety_tags",
            joinColumns = @JoinColumn(name = "tags_id"),
            inverseJoinColumns = @JoinColumn(name = "poetys_id")
    )
    private List<Picture> poetiesWithThisTags;

    @ManyToMany
    @JoinTable(
            name = "gif_tags",
            joinColumns = @JoinColumn(name = "tags_id"),
            inverseJoinColumns = @JoinColumn(name = "gifs_id")
    )
    private List<Picture> gifsWithThisTags;

    @ManyToMany
    @JoinTable(
            name = "song_tags",
            joinColumns = @JoinColumn(name = "tags_id"),
            inverseJoinColumns = @JoinColumn(name = "songs_id")
    )
    private List<Picture> songsWithThisTags;

    @ManyToMany
    @JoinTable(
            name = "video_tags",
            joinColumns = @JoinColumn(name = "tags_id"),
            inverseJoinColumns = @JoinColumn(name = "videos_id")
    )
    private List<Picture> videosWithThisTags;

    public Tags(String tagName, int tagScore, List<Picture> picturesWithThisTags, List<Picture> quotesWithThisTags, List<Picture> poetiesWithThisTags, List<Picture> gifsWithThisTags, List<Picture> songsWithThisTags, List<Picture> videosWithThisTags) {
        this.tagName = tagName;
        this.tagScore = tagScore;
        this.picturesWithThisTags = picturesWithThisTags;
        this.quotesWithThisTags = quotesWithThisTags;
        this.poetiesWithThisTags = poetiesWithThisTags;
        this.gifsWithThisTags = gifsWithThisTags;
        this.songsWithThisTags = songsWithThisTags;
        this.videosWithThisTags = videosWithThisTags;
    }
}
