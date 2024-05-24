package com.management.task.business.service;

import com.management.task.business.dto.TaskDto;
import com.management.task.business.dto.TaskRequest;
import com.management.task.business.exceptions.TaskNotFoundException;
import com.management.task.persistence.model.Task;
import com.management.task.persistence.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final GoogleCalendarService service;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, GoogleCalendarService service){
        this.taskRepository = taskRepository;
        this.service = service;
    }

    @Override
    public TaskDto createTask(TaskRequest taskRequest){
        String eventId = "null";
        try {
            eventId = service.createEvent(taskRequest);
        } catch (GeneralSecurityException | IOException e) {
            System.out.println(e.getMessage());
        }
        Task task = new Task();
        task.setEventId(eventId);
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setSummary(taskRequest.getSummary());
        task.setStart(taskRequest.getStart());
        task.setEnd(taskRequest.getEnd());

        taskRepository.saveAndFlush(task);
        return convertTaskToDto(task);
    }

    @Override
    @Cacheable(value = "tasks", key = "#id")
    public TaskDto getTaskById(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));
        return convertTaskToDto(task);
    }

    @Override
    @Cacheable(value = "tasks")
    public List<TaskDto> getAllTasks(){
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream()
                .map(this::convertTaskToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "tasks", key = "#pageable")
    public Page<TaskDto> getAllTasks(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);
        return taskPage.map(this::convertTaskToDto);
    }

    private TaskDto convertTaskToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getEventId(),
                task.getSummary(),
                task.getDescription(),
                task.getStart(),
                task.getEnd()
        );
    }

    @Override
    @CachePut(value = "tasks", key = "#id")
    public TaskDto updateTask(int id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setSummary(taskRequest.getSummary());
        task.setStart(taskRequest.getStart());
        task.setEnd(taskRequest.getEnd());

        taskRepository.save(task);
        return convertTaskToDto(task);
    }

    @Override
    @CachePut(value = "tasks", key = "#id")
    public TaskDto partialUpdateTask(int id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));

        if (taskRequest.getTitle() != null) {
            task.setTitle(taskRequest.getTitle());
        }
        if (taskRequest.getDescription() != null) {
            task.setDescription(taskRequest.getDescription());
        }
        if (taskRequest.getSummary() != null) {
            task.setSummary(taskRequest.getSummary());
        }
        if (taskRequest.getStart() != null) {
            task.setStart(taskRequest.getStart());
        }
        if (taskRequest.getEnd() != null) {
            task.setEnd(taskRequest.getEnd());
        }

        taskRepository.save(task);
        return convertTaskToDto(task);
    }

    @Override
    @CacheEvict(value = "tasks", key = "#id")
    public void deleteTask(int id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }
}
