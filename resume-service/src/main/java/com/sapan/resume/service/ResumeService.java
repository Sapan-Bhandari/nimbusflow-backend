package com.sapan.resume.service;

import com.sapan.resume.client.JobClient;
import com.sapan.resume.client.MatcherClient;

import com.sapan.resume.dto.JobDto;
import com.sapan.resume.dto.MatchRequest;
import com.sapan.resume.dto.MatchResponse;

import com.sapan.resume.entity.Resume;

import com.sapan.resume.exception.FileProcessingException;
import com.sapan.resume.exception.ResumeNotFoundException;

import com.sapan.resume.repository.ResumeRepository;

import com.sapan.resume.util.ResumeParser;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository repository;

    private final MatcherClient matcherClient;

    private final JobClient jobClient;

    public String uploadResume(
            MultipartFile file) {

        try {

            String extractedText =
                    ResumeParser.extractText(file);

            Resume resume = new Resume();

            resume.setFileName(
                    file.getOriginalFilename()
            );

            resume.setContent(
                    extractedText
            );

            repository.save(resume);

            return "Resume uploaded successfully";

        } catch (Exception e) {

            throw new FileProcessingException(

                    "Error processing uploaded file"
            );
        }
    }

    public Resume getResumeById(
            Long id) {

        return repository.findById(id)

                .orElseThrow(() ->

                        new ResumeNotFoundException(

                                "Resume not found with ID: "
                                        + id
                        )
                );
    }

    public MatchResponse matchResumeWithJob(

            Long resumeId,

            Long jobId
    ) {

        Resume resume =
                getResumeById(resumeId);

        JobDto job =
                jobClient.getJob(jobId);

        MatchRequest request =
                new MatchRequest();

        request.setResumeText(
                resume.getContent()
        );

        request.setJobDescription(

                job.getDescription()
                        + " "
                        + job.getSkills()
        );

        return matcherClient.getMatchAnalysis(
                request
        );
    }

    public List<Resume> getAllResumes() {

        return repository.findAll();
    }
}