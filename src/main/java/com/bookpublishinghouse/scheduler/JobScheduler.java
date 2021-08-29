package com.bookpublishinghouse.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job that runs every one hour and generates a report
 */
@Component
public class JobScheduler {

    private final ReportGenerator reportGenerator;

    @Autowired
    public JobScheduler(ReportGenerator reportGenerator) {

        this.reportGenerator = reportGenerator;
    }

    @Scheduled(cron = "0 0 0/1 1/1 * ?")
    public void runScheduledJob() {

        reportGenerator.generateReport();
    }
}
