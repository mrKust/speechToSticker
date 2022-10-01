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

    @Column(name = "picture_name")
    private String pictureName;

    @Column(name = "address")
    private String pictureURL;

    @Column(name = "picture_for_weight")
    private int pictureWeight;
}
