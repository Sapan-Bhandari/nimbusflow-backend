package com.sapan.resume.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.Loader;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class ResumeParser {

    public static String extractText(MultipartFile file) throws Exception {

        String fileName = file.getOriginalFilename();

        if (fileName == null) {
            return "";
        }

        fileName = fileName.toLowerCase();

        // TXT
        if (fileName.endsWith(".txt")) {

            return new String(file.getBytes());
        }

        // PDF
        else if (fileName.endsWith(".pdf")) {

            PDDocument document = Loader.loadPDF(file.getBytes());

            PDFTextStripper stripper = new PDFTextStripper();

            String text = stripper.getText(document);

            document.close();

            return text;
        }

        // DOCX
        else if (fileName.endsWith(".docx")) {

            InputStream is = file.getInputStream();

            XWPFDocument doc = new XWPFDocument(is);

            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);

            String text = extractor.getText();

            extractor.close();
            doc.close();

            return text;
        }

        // Unsupported
        else {

            return "Unsupported file format";
        }
    }
}
