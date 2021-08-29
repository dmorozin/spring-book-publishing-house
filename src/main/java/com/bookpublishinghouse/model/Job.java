package com.bookpublishinghouse.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime runAt;

    private boolean success;

    private String message;

    public Job() {

    }

    public Job(LocalDateTime runAt, boolean success, String message) {

        this.runAt = runAt;
        this.success = success;
        this.message = message;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public LocalDateTime getRunAt() {

        return runAt;
    }

    public void setRunAt(LocalDateTime runAt) {

        this.runAt = runAt;
    }

    public boolean isSuccess() {

        return success;
    }

    public void setSuccess(boolean success) {

        this.success = success;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }
}
