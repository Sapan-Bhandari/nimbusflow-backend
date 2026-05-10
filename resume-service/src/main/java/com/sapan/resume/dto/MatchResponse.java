package com.sapan.resume.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;

@Data
public class MatchResponse {

    @JsonProperty("match_score")
    private Double matchScore;

    @JsonProperty("semantic_score")
    private Double semanticScore;

    @JsonProperty("alignment_score")
    private Double alignmentScore;

    @JsonProperty("matched_skills")
    private List<String> matchedSkills;

    @JsonProperty("missing_skills")
    private List<String> missingSkills;

    @JsonProperty("ai_recommendations")
    private String aiRecommendations;
}