package com.arsene.eportfolio.services;

import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class CvServiceImpl implements CvService{

    private final TemplateEngine templateEngine;

    public CvServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] renderCv(WebContext ctx) {

        String processedHtml = templateEngine.process("CV", ctx);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml);
            renderer.layout();
            renderer.createPDF(os);

            return os.toByteArray();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}

