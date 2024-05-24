package com.management.task.business.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

@Value
public class TaskDto implements Serializable {
    Integer id;
    String title;
    String eventId;
    String summary;
    String description;
    Instant start;
    Instant end;
}