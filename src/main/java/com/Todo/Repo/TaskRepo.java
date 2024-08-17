package com.Todo.Repo;

import com.Todo.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByCreatedBy(Integer createdBy);
}
