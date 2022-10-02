package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "Tags")
@Getter
@Setter
@NoArgsConstructor
public class Tags {

    @Id
    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "id_gif")
    private String idGif;

    @Column(name = "id_picture")
    private String idPic;

    @Column(name = "id_poety")
    private String idPoety;

    @Column(name = "id_quote")
    private String idQuote;

    @Column(name = "id_song")
    private String idSong;

    @Column(name = "id_video")
    private String idVideo;
}
