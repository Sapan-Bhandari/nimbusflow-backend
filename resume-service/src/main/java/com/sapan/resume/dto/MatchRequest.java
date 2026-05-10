package com.sapan.resume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MatchRequest {

    @JsonProperty("resume_text")
    private String resumeText;

    @JsonProperty("job_description")
    private String jobDescription;
}