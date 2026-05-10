package com.sapan.job.controller;

import com.sapan.job.entity.Job;
import com.sapan.job.service.JobService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService service;

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {

        return ResponseEntity.ok(service.createJob(job));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable("id") Long id) {

        return ResponseEntity.ok(service.getJob(id));
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {

        return ResponseEntity.ok(service.getAllJobs());
    }
}