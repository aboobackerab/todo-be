package com.Todo.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
