package com.bookpublishinghouse.repository;

import com.bookpublishinghouse.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    Optional<Job> findFirstBySuccessTrueOrderByRunAtDesc();
}
