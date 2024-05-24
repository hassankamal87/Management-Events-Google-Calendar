package com.management.task.business.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@Getter
public class TaskRequest implements Serializable {
    @NotBlank(message = "Title is mandatory")
    @Size(max = 50)
    String title;

    @Size(max = 45)
    String summary;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 255)
    String description;

    @NotNull(message = "Start Time is mandatory")
    @Future(message = "Start Time must be in the future")
    Instant start;

    @NotNull(message = "End Time is mandatory")
    @Future(message = "End Time must be in the future")
    Instant end;
}
