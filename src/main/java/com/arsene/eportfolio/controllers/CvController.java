package com.arsene.eportfolio.controllers;

import com.arsene.eportfolio.model.data.DiplomaRepository;
import com.arsene.eportfolio.model.data.ProjectRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.services.CvService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static org.springframework.web.servlet.support.RequestContextUtils.getLocale;


@RestController
@RequestMapping("/cv")
@AllArgsConstructor
public class CvController {
    private ServletContext servletContext;

    private CvService cvService;
    private SubjectRepository subjectRepository;
    private ProjectRepository projectRepository;
    private DiplomaRepository diplomaRepository;

    @GetMapping("")
    public ResponseEntity<Resource> getCv(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        Locale locale = getLocale(servletRequest);
        WebContext context = new WebContext(servletRequest, servletResponse, servletContext, locale);
        context.setVariable("subjects", subjectRepository.findAll());
        context.setVariable("projects", projectRepository.findAll());
        context.setVariable("diplomas", diplomaRepository.findAll());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "CV_ARSENE_LAPOSTOLET.pdf" + "\"")
                .body(new ByteArrayResource(cvService.renderCv(context)));
    }

}
