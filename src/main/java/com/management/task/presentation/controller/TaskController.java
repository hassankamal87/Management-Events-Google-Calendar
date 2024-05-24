package com.management.task.presentation.controller;

import com.management.task.business.dto.TaskDto;
import com.management.task.business.dto.TaskRequest;
import com.management.task.business.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequestMapping("v1/tasks")
@RestController
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        try {
            TaskDto taskDto = taskService.createTask(taskRequest);
            URI location = new URI("v1/tasks/" + taskDto.getId());
            return ResponseEntity.created(location).body(taskDto);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("id") int id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping(path = "page")
    public ResponseEntity<Page<TaskDto>> getTasks(Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasks(pageable));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable("id") int id, @Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.updateTask(id, taskRequest));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<TaskDto> partialUpdateTask(@PathVariable("id") int id, @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.partialUpdateTask(id, taskRequest));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") int id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTasks() {
        taskService.deleteAllTasks();
        return ResponseEntity.noContent().build();
    }
}
