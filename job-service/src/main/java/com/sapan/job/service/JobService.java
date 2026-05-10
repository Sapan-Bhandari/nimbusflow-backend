package com.sapan.job.service;

import com.sapan.job.entity.Job;
import com.sapan.job.exception.JobNotFoundException;
import com.sapan.job.repository.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository repository;

    public Job createJob(Job job) {
        return repository.save(job);
    }

    public Job getJob(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new JobNotFoundException("Job not found with ID: " + id));
    }

    public List<Job> getAllJobs() {
        return repository.findAll();
    }
}