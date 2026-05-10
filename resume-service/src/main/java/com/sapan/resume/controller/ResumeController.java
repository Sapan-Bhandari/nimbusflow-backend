package com.sapan.resume.controller;

import com.sapan.resume.dto.MatchResponse;

import com.sapan.resume.entity.Resume;

import com.sapan.resume.service.ResumeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(

            @RequestParam("file")
            MultipartFile file
    ) {

        String response =
                resumeService.uploadResume(file);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResume(

            @PathVariable("id")
            Long id
    ) {

        Resume resume =
                resumeService.getResumeById(id);

        return ResponseEntity.ok(resume);
    }

    @GetMapping("/match/{resumeId}/job/{jobId}")
    public ResponseEntity<MatchResponse> matchResume(

            @PathVariable("resumeId")
            Long resumeId,

            @PathVariable("jobId")
            Long jobId
    ) {

        MatchResponse response =

                resumeService.matchResumeWithJob(

                        resumeId,

                        jobId
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Resume>> getAllResumes() {

        return ResponseEntity.ok(

                resumeService.getAllResumes()
        );
    }
}