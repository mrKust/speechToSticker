package com.ebka.speech.dto;

import lombok.Getter;

@Getter
public class PairDTO {
    private String paramOne;
    private String paramTwo;

    public PairDTO(String paramOne, String paramTwo) {
        this.paramOne = paramOne;
        this.paramTwo = paramTwo;
    }
}
