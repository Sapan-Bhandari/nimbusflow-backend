package com.sapan.resume.client;

import com.sapan.resume.dto.JobDto;

import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JobClient {

    private final WebClient webClient;

    public JobClient() {

        this.webClient = WebClient.builder()
                .baseUrl("http://job-service:8082")
                .build();
    }

    public JobDto getJob(Long id) {

        return webClient.get()
                .uri("/jobs/" + id)
                .retrieve()
                .bodyToMono(JobDto.class)
                .block();
    }
}