package com.bookpublishinghouse.scheduler;

import com.bookpublishinghouse.model.Book;
import com.bookpublishinghouse.model.Job;
import com.bookpublishinghouse.repository.BookRepository;
import com.bookpublishinghouse.repository.JobRepository;
import com.bookpublishinghouse.utils.Constants;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReportGeneratorImpl implements ReportGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportGeneratorImpl.class);

    private final BookRepository bookRepository;
    private final JobRepository jobRepository;
    private final EmailSendingService emailSendingService;

    @Value("${email-to}")
    private String email;

    @Autowired
    public ReportGeneratorImpl(BookRepository bookRepository, JobRepository jobRepository, EmailSendingService emailSendingService) {

        this.bookRepository = bookRepository;
        this.jobRepository = jobRepository;
        this.emailSendingService = emailSendingService;
    }

    public void generateReport() {

        String message;
        LocalDateTime generationStartTime = LocalDateTime.now();

        Optional<Job> lastRunJob = jobRepository.findFirstBySuccessTrueOrderByRunAtDesc();
        List<Book> books = lastRunJob.isPresent() ?
                bookRepository.findAllByCreatedAtAfter(lastRunJob.get().getRunAt()) : bookRepository.findAll();

        if (!books.isEmpty()) {
            boolean success = false;
            List<ReportData> reportData = books.stream()
                                               .map(book -> new ReportData(book.getIsbn()))
                                               .collect(Collectors.toList());
            try {
                ByteArrayOutputStream byteArrayOutputStream = exportReport(reportData);
                String dateTime = generationStartTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                PdfEmailAttachment attachment = new PdfEmailAttachment(byteArrayOutputStream, "ISBN Report " + dateTime + ".pdf");
                emailSendingService.sendEmail(email,
                                              "ISBN Report",
                                              "This report contains ISBNs of recently published books",
                                              attachment);
                success = true;
                message = Constants.SUCCESSFUL_REPORT;
            } catch (Exception e) {
                LOGGER.error("Could not generate the report", e);
                message = e.getMessage();
            }

            jobRepository.save(new Job(generationStartTime, success, message));
        }
    }

    private ByteArrayOutputStream exportReport(List<ReportData> reportData) {

        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        File file;
        try {
            file = ResourceUtils.getFile("classpath:isbnReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("title", "ISBNs of recent books");

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint, pdfStream);
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not find file", e);
        } catch (JRException e) {
            LOGGER.error("Could not build the report", e);
        }

        return pdfStream;
    }

}
