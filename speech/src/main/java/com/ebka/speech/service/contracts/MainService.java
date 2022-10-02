package com.ebka.speech.service.contracts;


import com.ebka.speech.dto.PairDTO;

import java.util.Map;

public interface MainService {

    public PairDTO getEmotion(PairDTO inputData);
}
