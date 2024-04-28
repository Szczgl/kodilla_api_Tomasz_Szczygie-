package com.crud.tasks.domain;

import com.crud.tasks.domain.Trello;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrelloTest {

    @Test
    public void testTrello() {

        //GIVEN
        Trello trello = new Trello();
        trello.setBoard(1);
        trello.setCard(2);

        //WHEN

        //THEN
        assertEquals(1, trello.getBoard());
        assertEquals(2, trello.getCard());
    }

    @Test
    public void testTrelloDto() {

        //GIVEN
        TrelloDto trelloDto = new TrelloDto();
        trelloDto.setBoard(1);
        trelloDto.setCard(2);

        //WHEN

        //THEN
        assertEquals(1, trelloDto.getBoard());
        assertEquals(2, trelloDto.getCard());
    }
}
