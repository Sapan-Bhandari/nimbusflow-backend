package com.sapan.job.repository;

import com.sapan.job.entity.Job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository
        extends JpaRepository<Job, Long> {
}