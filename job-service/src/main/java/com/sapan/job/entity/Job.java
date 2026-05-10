package com.sapan.job.entity;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String company;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String skills;
}