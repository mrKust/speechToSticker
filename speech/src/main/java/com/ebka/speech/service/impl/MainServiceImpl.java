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
    public PairDTO getEmotion(PairDTO inputData) {
        //Map<String, String> result = new HashMap<>();
        PairDTO answer = new PairDTO();
        System.out.println();
        int[] choosenTypes = Stream.of(inputData.getParamTwo().split(",")).map(elem->elem.trim())
                .mapToInt(Integer::parseInt)
                .toArray();
        Random random = new Random();
        int mediaPos = random.nextInt(choosenTypes.length);
        int[] idElems;
        int resultId;
        switch (mediaPos){
            case 0:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdGif().split(",")).map(elem->elem.trim())
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Gif gif = gifService.getGif(idElems[resultId]);
                if (gif != null){
                    answer.setParamOne("gif");
                    answer.setParamTwo(gif.getGifUrl());
                    //result.put("gif",gif.getGifUrl());
                }
                break;
            case 1:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdPic().split(",")).map(elem->elem.trim())
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Picture picture = pictureService.getPicture(idElems[resultId]);
                if (picture != null){
                    answer.setParamOne("picture");
                    answer.setParamTwo(picture.getPictureURL());
                    //result.put("picture",picture.getPictureURL());
                }
                break;
            case 2:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdPoety().split(",")).map(elem->elem.trim())
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Poety poety = poetyService.getPoety(idElems[resultId]);
                if (poety != null){
                    answer.setParamOne("poety");
                    answer.setParamTwo(poety.getText()+"/n"+poety.getAuthor());

                    //result.put("poety",poety.getText()+"/n"+poety.getAuthor());
                }
                break;
            case 3:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdQuote().split(",")).map(elem->elem.trim())
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Quote quote = quoteService.getQuote(idElems[resultId]);
                if (quote != null){
                    answer.setParamOne("quote");
                    answer.setParamTwo(quote.getText()+"/n"+quote.getAuthor());

                    //result.put("quote",quote.getText()+"/n"+quote.getAuthor());
                }
                break;
            case 4:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdSong().split(",")).map(elem->elem.trim())
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Song song = songService.getSong(idElems[resultId]);
                if (song != null){
                    answer.setParamOne("song");
                    answer.setParamTwo(song.getSongUrl());

//                    result.put("song",song.getSongUrl());
                }
                break;
            case 5:
                idElems = Stream.of(tagsService.getTags(inputData.getParamOne()).getIdVideo().split(",")).map(elem->elem.trim())
                        .mapToInt(Integer::parseInt)
                        .toArray();
                resultId = random.nextInt(idElems.length);
                Video video = videoService.getVideo(idElems[resultId]);
                if (video != null){

                    answer.setParamOne("video");
                    answer.setParamTwo(video.getSongUrl());
                    //result.put("video",video.getSongUrl());
                }
                break;
            default:
                return null;
        }
        return answer;
    }
}
