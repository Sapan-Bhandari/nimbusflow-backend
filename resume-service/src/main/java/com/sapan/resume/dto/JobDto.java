package com.sapan.resume.dto;

import lombok.Data;

@Data
public class JobDto {

    private Long id;
    private String title;
    private String company;
    private String description;
    private String skills;
}