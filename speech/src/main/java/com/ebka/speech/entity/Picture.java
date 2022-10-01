package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "picture_rating")
    private int pictureScore;

    @ManyToMany
    @JoinTable(
            name = "picture_tags",
            joinColumns = @JoinColumn(name = "pictures_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    private List<Tags> tagsForPicture;

    public Picture(String pictureURL, int pictureScore, List<Tags> tagsForPicture) {
        this.pictureURL = pictureURL;
        this.pictureScore = pictureScore;
        this.tagsForPicture = tagsForPicture;
    }
}
