package com.bookpublishinghouse.scheduler;

/**
 *  Generates Jasper report with the list of recent book isbns and sends it by email
 */
public interface ReportGenerator {

    void generateReport();
}
