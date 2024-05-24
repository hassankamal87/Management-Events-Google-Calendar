package com.management.task.persistence.repo;

import com.management.task.persistence.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
