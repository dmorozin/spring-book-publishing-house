package com.bookpublishinghouse.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

@Component
public class EmailSendingServiceImpl implements EmailSendingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSendingServiceImpl.class);

    private final JavaMailSender emailSender;

    @Autowired
    public EmailSendingServiceImpl(JavaMailSender emailSender) {

        this.emailSender = emailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String text, PdfEmailAttachment attachment) {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.setFrom("fake-sending");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            DataSource dataSource = new ByteArrayDataSource(attachment.getPdfStream().toByteArray(),
                                                            "application/pdf");

            helper.addAttachment(attachment.getName(), dataSource);

            emailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Could not send an email", e);
        }
    }
}
