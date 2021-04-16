package com.arsene.eportfolio.services;

import org.thymeleaf.context.WebContext;

public interface CvService {

    byte[] renderCv(WebContext ctx);

}
