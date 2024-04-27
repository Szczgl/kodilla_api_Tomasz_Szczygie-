package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MapperTest {

    @InjectMocks
    private TaskMapper taskMapper;

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void testTaskToMap() {

        //GIVEN
        TaskDto taskDto = new TaskDto(1L, "new task", "test new task");

        //WHEN
        Task task = taskMapper.mapToTask(taskDto);

        //THEN
        assertEquals(taskDto.getId(), task.getId());
        assertEquals("new task", task.getTitle());
        assertEquals("test new task", task.getContent());
    }

    @Test
    public void testMapToTaskDto() {

        //GIVEN
        Task task = new Task(1L, "new task1", "test new task1");

        //WHEN
        TaskDto taskDto = taskMapper.mapToTasksDto(task);

        //THEN
        assertEquals(task.getId(), taskDto.getId());
        assertEquals("new task1", taskDto.getTitle());
        assertEquals(task.getContent(), taskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoList() {

        //GIVEN
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L,"new task2", "test new task2"));
        tasks.add(new Task(2L, "new task3", "test new task3"));
        tasks.add(new Task(3L, "new task4", "test new task4"));

        //WHEN
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        //THEN
        assertEquals(3, taskDtos.size());
        assertEquals(tasks.get(0).getId(), taskDtos.get(0).getId());
        assertEquals("new task3", taskDtos.get(1).getTitle());
        assertNotEquals("test new task3", taskDtos.get(2).getContent());
    }

    @Test
    public void testMapToBoards() {

        //GIVEN
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        List<TrelloListDto> trelloListDtos1 = new ArrayList<>();
        List<TrelloListDto> trelloListDtos2 = new ArrayList<>();

        trelloListDtos1.add(new TrelloListDto("1","Test1", true));
        trelloListDtos1.add(new TrelloListDto("2", "Test2", true));
        trelloListDtos2.add(new TrelloListDto("3", "Test3", false));
        trelloListDtos2.add(new TrelloListDto("4", "Test4", false));
        trelloBoardDtos.add(new TrelloBoardDto("1", "Test5", trelloListDtos1));
        trelloBoardDtos.add(new TrelloBoardDto("2", "Test6", trelloListDtos2));

        //WHEN
        List<TrelloBoard> trelloBoards =trelloMapper.mapToBoards(trelloBoardDtos);

        //THEN
        assertEquals(2, trelloBoards.size());
        assertEquals("1", trelloBoards.get(0).getId());
        assertEquals("Test6", trelloBoards.get(1).getName());
        assertEquals(2, trelloBoards.get(0).getLists().size());
        assertFalse(trelloBoards.get(1).getLists().get(1).isClosed());
    }

    @Test
    public void testMapToBoardsDto() {

        //GIVEN
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        List<TrelloList> trelloLists1 = new ArrayList<>();
        List<TrelloList> trelloLists2 = new ArrayList<>();

        trelloLists1.add(new TrelloList("5","Test1", false));
        trelloLists1.add(new TrelloList("6","Test2", false));
        trelloLists2.add(new TrelloList("7","Test3", true));
        trelloLists2.add(new TrelloList("8","Test4", true));
        trelloBoards.add(new TrelloBoard("3","Test5", trelloLists1));
        trelloBoards.add(new TrelloBoard("4","Test6", trelloLists2));

        //WHEN
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);

        //THEN
        assertEquals(2, trelloBoardDtos.size());
        assertEquals("3", trelloBoardDtos.get(0).getId());
        assertEquals("Test6", trelloBoardDtos.get(1).getName());
        assertEquals(2, trelloBoardDtos.get(0).getLists().size());
        assertTrue(trelloBoardDtos.get(1).getLists().get(1).isClosed());
    }

    @Test
    public void testMapToList() {

        //GIVEN
        List<TrelloListDto> trelloListDtos = new ArrayList<>();

        trelloListDtos.add(new TrelloListDto("1","Test1", true));
        trelloListDtos.add(new TrelloListDto("2", "Test2", false));
        trelloListDtos.add(new TrelloListDto("3", "Test3", true));
        trelloListDtos.add(new TrelloListDto("4", "Test4", false));

        //WHEN
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);

        //THEN
        assertEquals(4, trelloLists.size());
        assertEquals("2", trelloLists.get(1).getId());
        assertEquals("Test3", trelloLists.get(2).getName());
        assertFalse(trelloLists.get(3).isClosed());
    }

    @Test
    public void testMapToListsDto() {

        //GIVEN
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1","Test1", true));
        trelloLists.add(new TrelloList("2", "Test2", false));
        trelloLists.add(new TrelloList("3", "Test3", false));
        trelloLists.add(new TrelloList("4", "Test4", false));

        //WHEN
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        //THEN
        assertEquals(4, trelloListDtos.size());
        assertEquals("2", trelloListDtos.get(1).getId());
        assertEquals("Test3", trelloListDtos.get(2).getName());
        assertFalse(trelloListDtos.get(3).isClosed());
    }

    @Test
    public void testMapToCard() {

        //GIVEN
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test1", "description test1", "pos1","1");

        //WHEN
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //THEN
        assertEquals("Test1", trelloCard.getName());
        assertEquals("description test1", trelloCard.getDescription());
        assertEquals("pos1", trelloCard.getPos());
        assertEquals("1", trelloCard.getListId());
    }

    @Test
    public void testMapToCardDto() {

        //GIVEN
        TrelloCard trelloCard = new TrelloCard("Test2", "description test2", "pos2","2");

        //WHEN
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //THEN
        assertEquals("Test2", trelloCardDto.getName());
        assertEquals("description test2", trelloCardDto.getDescription());
        assertEquals("pos2", trelloCardDto.getPos());
        assertEquals("2", trelloCardDto.getListId());
    }
}