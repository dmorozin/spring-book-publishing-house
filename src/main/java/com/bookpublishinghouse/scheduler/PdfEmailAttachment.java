package com.bookpublishinghouse.scheduler;

import java.io.ByteArrayOutputStream;

public class PdfEmailAttachment {

    private ByteArrayOutputStream pdfStream;
    private String name;

    public PdfEmailAttachment(ByteArrayOutputStream pdfStream, String name) {

        this.pdfStream = pdfStream;
        this.name = name;
    }

    public ByteArrayOutputStream getPdfStream() {

        return pdfStream;
    }

    public void setPdfStream(ByteArrayOutputStream pdfStream) {

        this.pdfStream = pdfStream;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
