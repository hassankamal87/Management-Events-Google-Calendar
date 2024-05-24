package com.management.task.business.service;

import com.management.task.business.dto.TaskDto;
import com.management.task.business.dto.TaskRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    TaskDto createTask(TaskRequest taskRequest);
    TaskDto getTaskById(int id);
    List<TaskDto> getAllTasks();
    Page<TaskDto> getAllTasks(Pageable pageable);
    TaskDto updateTask(int id, TaskRequest taskRequest);
    TaskDto partialUpdateTask(int id, TaskRequest taskRequest);
    void deleteTask(int id);
    void deleteAllTasks();
}
