package com.Todo.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDTO {

        private String name;

        private String description;

        private Timestamp createdTime;

        private Timestamp endTime;

        private Timestamp modifiedTime;

        private String status;

        private Integer createdBy;

        private String priority;

        private String label;
}
