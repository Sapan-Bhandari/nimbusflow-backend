package com.sapan.resume.entity;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String content;
}