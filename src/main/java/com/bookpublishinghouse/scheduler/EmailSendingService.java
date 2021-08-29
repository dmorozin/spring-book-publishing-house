package com.bookpublishinghouse.scheduler;

/**
 * Service responsible for sending email with generated report pdf as attachment.
 */
public interface EmailSendingService {

    /**
     * Sends email with attachment
     *
     * @param to         Email recipient
     * @param subject    Email subject
     * @param text       Email text content
     * @param attachment Contains pdf stream and file name.
     */
    void sendEmail(String to,
                   String subject,
                   String text,
                   PdfEmailAttachment attachment);
}
