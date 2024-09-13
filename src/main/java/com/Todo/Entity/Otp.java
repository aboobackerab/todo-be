package com.Todo.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer otp;

    private Integer verified;

    private String email;

    private Timestamp createdDate = new Timestamp(System.currentTimeMillis());
}
