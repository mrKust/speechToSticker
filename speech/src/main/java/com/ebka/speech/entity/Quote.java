package com.ebka.speech.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Quote")
@Getter
@NoArgsConstructor
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    private String text;

    @Column(name = "author")
    private String author;

    @Column(name = "quote_score")
    private int quoteScore;

    @ManyToMany
    @JoinTable(
            name = "quote_tags",
            joinColumns = @JoinColumn(name = "quotes_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id")
    )
    private List<Tags> tagsForQuote;

    public Quote(String text, String author, int quoteScore, List<Tags> tagsForQuote) {
        this.text = text;
        this.author = author;
        this.quoteScore = quoteScore;
        this.tagsForQuote = tagsForQuote;
    }
}
