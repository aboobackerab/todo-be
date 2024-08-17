package com.Todo.Service.Impl;


import com.Todo.DTO.TaskDTO;
import com.Todo.Entity.Task;
import com.Todo.Repo.TaskRepo;
import com.Todo.Service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Override
    public ResponseEntity<List<TaskDTO>> getTasks(Integer userId) {
        List<Task> tasks = taskRepo.findByCreatedBy(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        List<TaskDTO> taskDTOList = tasks.stream().map(task -> objectMapper.convertValue(task, TaskDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(taskDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TaskDTO> addTask(TaskDTO taskDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        Task task = objectMapper.convertValue(taskDTO, Task.class);
        taskRepo.save(task);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }
}
