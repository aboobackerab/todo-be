package com.Todo.Service;

import com.Todo.DTO.TaskDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {


    public ResponseEntity<TaskDTO> addTask(TaskDTO taskDTO);

    public ResponseEntity<List<TaskDTO>> getTasks(Integer userId);
}
