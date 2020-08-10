package ru.ag.TimeTracker.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.ag.TimeTracker.model.Task;
import ru.ag.TimeTracker.repository.UserRepository;
import ru.ag.TimeTracker.service.TaskService;
import ru.ag.TimeTracker.service.UserService;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private TaskService taskService;


    @Test
    public void getAllTasks() throws Exception {
        Date date = new Date();

        when(taskService.findAll(2L)).thenReturn(Arrays.asList(
                new Task(1L, 2L, "description1", "Status1", date, date),
                new Task(3L, 2L, "description1", "Status1", date, date)
        ));
        mockMvc.perform(get("/user/2/task"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTaskById() throws Exception {
        Date date = new Date();

        when(taskService.findById(2L, 2L)).thenReturn(Optional.of(
                new Task(1L, 2L, "description1", "Status1", date, date)
        ));
        mockMvc.perform(get("/user/2/task/1"))
                .andExpect(status().isOk());
    }

}
