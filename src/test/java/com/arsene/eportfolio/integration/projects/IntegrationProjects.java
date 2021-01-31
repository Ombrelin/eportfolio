package com.arsene.eportfolio.integration.projects;

import com.arsene.eportfolio.EportfolioApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EportfolioApplication.class)
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestPropertySource(properties = {"username=John Shepard", "password=$2y$10$zVpNcmtFnya0GIOb6v3dBeadXX.lVnO2enKc4Uh05bz2Cg63/SLnm"})

public class IntegrationProjects {


    // GET /projects
    // PUT /projects/{id}
    // DELETE /projects/{id}
    // POST /projects
    // POST /projects/{id}/technologies
    // POST /projects​/{id}/abilities
    // DELETE /projects/{id}/technologie/
    // DELETE/projects​/{id}/abilities

}
