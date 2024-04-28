package com.crud.tasks.service;

import com.crud.tasks.domain.Task;

import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DBServiceTest {
    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void testGetAllTask() {

        //GIVEN
        List<Task> tasks = List.of(new Task(1L, "Test", "test"));
        when(taskRepository.findAll()).thenReturn(tasks);

        //WHEN
        List<Task> resultAllTask = dbService.getAllTasks();

        //THEN
        assertEquals(1, resultAllTask.size());
    }

    @Test
    public void testSaveTask() {

        //GIVEN
        Task task = new Task(1L, "Test", "test");
        when(taskRepository.save(task)).thenReturn(task);

        //WHEN
        Task result = dbService.saveTask(task);

        //THEN
        assertEquals(1, result.getId());
        assertEquals("Test", result.getTitle());
        assertEquals("test", result.getContent());
    }

}
