package com.ebka.speech.service.impl;

import com.ebka.speech.dto.PairDTO;
import com.ebka.speech.entity.*;
import com.ebka.speech.service.contracts.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class MainServiceImpl implements MainService {

    private GifService gifService;
    private PictureService pictureService;
    private PoetyService poetyService;
    private QuoteService quoteService;
    private SongService songService;
    private TagsService tagsService;
    private VideoService videoService;

    public MainServiceImpl(GifService gifService, PictureService pictureService,
                           PoetyService poetyService, QuoteService quoteService,
                           SongService songService, TagsService tagsService,
                           VideoService videoService) {
        this.gifService = gifService;
        this.pictureService = pictureService;
        this.poetyService = poetyService;
        this.quoteService = quoteService;
        this.songService = songService;
        this.tagsService = tagsService;
        this.videoService = videoService;
    }

    @Override
    public Map getEmotion(PairDTO inputData) {
        Map<String, String> result = new HashMap<>();
        int[] choosenTypes = Stream.of(inputData.getParamTwo().split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();
        Random random = new Random();
        int mediaPos = random.nextInt(choosenTypes.length);
        int[] idElems;
        int resultId;
        switch (mediaPos){
            case 0:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdGif().split(", "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Gif gif = gifService.getGif(resultId);
                if (gif != null){
                    result.put("gif",gif.getGifUrl());
                }
                break;
            case 1:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdPic().split(", "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Picture picture = pictureService.getPicture(resultId);
                if (picture != null){
                    result.put("picture",picture.getPictureURL());
                }
                break;
            case 2:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdPoety().split(", "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Poety poety = poetyService.getPoety(resultId);
                if (poety != null){
                    result.put("poety",poety.getText()+"/n"+poety.getAuthor());
                }
                break;
            case 3:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdQuote().split(", "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Quote quote = quoteService.getQuote(resultId);
                if (quote != null){
                    result.put("quote",quote.getText()+"/n"+quote.getAuthor());
                }
                break;
            case 4:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdSong().split(", "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Song song = songService.getSong(resultId);
                if (song != null){
                    result.put("song",song.getSongUrl());
                }
                break;
            case 5:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdVideo().split(", "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Video video = videoService.getVideo(resultId);
                if (video != null){
                    result.put("video",video.getSongUrl());
                }
                break;
            default:
                return Collections.EMPTY_MAP;
        }
        return result;
    }
}
