package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.url.TrelloConfigUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TrelloClient {


    private final RestTemplate restTemplate;

    @Autowired
    TrelloConfigUrl trelloConfigUrl;

    public List<TrelloBoardDto> getTrelloBoards() {

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(bulidUrl(), TrelloBoardDto[].class);

        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    private URI bulidUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfigUrl.getTrelloApiEndpoint() + "/members/"
                + trelloConfigUrl.getTrelloUsername() + "/boards")
                .queryParam("key", trelloConfigUrl.getTrelloAppKey())
                .queryParam("token", trelloConfigUrl.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists","all")
                .build()
                .encode()
                .toUri();
        return url;
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfigUrl.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfigUrl.getTrelloAppKey())
                .queryParam("token", trelloConfigUrl.getTrelloToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }

    public CreatedTrelloCard createNewCardWithBadges(TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfigUrl.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfigUrl.getTrelloAppKey())
                .queryParam("token", trelloConfigUrl.getTrelloToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .queryParam("votes", trelloCardDto.getBadges().getVotes())
                .queryParam("attachmentsByType", trelloCardDto.getBadges().getAttachmentsByType())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }
}