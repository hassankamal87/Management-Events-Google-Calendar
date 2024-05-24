package com.management.task.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private Integer id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "summary", length = 45)
    private String summary;

    @Column(name = "description")
    private String description;

    @Column(name = "start")
    private Instant start;

    @Column(name = "end")
    private Instant end;

}