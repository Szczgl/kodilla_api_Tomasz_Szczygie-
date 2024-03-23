package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
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

    @Autowired
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
                .build()
                .encode()
                .toUri();
        return url;
    }
}