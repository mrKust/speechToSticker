package com.ebka.speech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PairDTO {
    private String paramOne;
    private String paramTwo;

    public PairDTO(String paramOne, String paramTwo) {
        this.paramOne = paramOne;
        this.paramTwo = paramTwo;
    }
}
