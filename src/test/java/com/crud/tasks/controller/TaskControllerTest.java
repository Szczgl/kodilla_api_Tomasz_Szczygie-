package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
class TaskControllerTest {

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetTasks() throws Exception {

        //GIVEN
        Task task = new Task(1L, "Test task", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test task", "Test content");
        when(dbService.getAllTasks()).thenReturn(Collections.singletonList(task));
        when(taskMapper.mapToTaskDtoList(any())).thenReturn(Collections.singletonList(taskDto));

        //WHEN

        //THEN
        mockMvc.perform(get("/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test task"))
                .andExpect(jsonPath("$[0].content").value("Test content"));
    }

    @Test
    void testDeleteTask() throws Exception {
        //GIVEN

        //WHEN

        //THEN
        mockMvc.perform(delete("/v1/tasks/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateTask() throws Exception {

        //GIVEN
        TaskDto taskDto = new TaskDto(null, "Test task", "Test content");
        Task task = new Task(1L, "Test task", "Test content");
        when(dbService.saveTask(any())).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //WHEN

        //THEN
        mockMvc.perform(post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTask() throws Exception {

        //GIVEN
        Task task = new Task(1L, "Test task", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test task", "Test content");
        when(taskMapper.mapToTasksDto(task)).thenReturn(taskDto);
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        //WHEN

        //THEN
        mockMvc.perform(put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test task"))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    @Test
    void getTask() throws Exception {

        //GIVEN
        Task task = new Task(1L, "Test task", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test task", "Test content");
        when(dbService.getTask(1L)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTasksDto(any())).thenReturn(taskDto);

        //WHEN

        //THEN
        mockMvc.perform(get("/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test task"))
                .andExpect(jsonPath("$.content").value("Test content"));
    }
}