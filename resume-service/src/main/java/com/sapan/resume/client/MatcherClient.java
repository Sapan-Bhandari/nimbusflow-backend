package com.sapan.resume.client;

import com.sapan.resume.dto.MatchRequest;
import com.sapan.resume.dto.MatchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class MatcherClient {

    private final WebClient webClient;

    public MatcherClient() {

        this.webClient = WebClient.builder()
                .baseUrl("http://matcher-service:8000")
                .build();
    }

    public MatchResponse getMatchAnalysis(
            MatchRequest request) {

        MatchResponse response =
                webClient.post()
                        .uri("/match")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(MatchResponse.class)
                        .block();

        log.info("Received AI Match Analysis: {}", response);
        return response;
    }
}