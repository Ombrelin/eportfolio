package com.arsene.eportfolio.integration.projects;

import com.arsene.eportfolio.EportfolioApplication;
import com.arsene.eportfolio.integration.IntegrationUtil;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.ProjectRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Project;
import com.arsene.eportfolio.model.entities.Technology;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EportfolioApplication.class)
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestPropertySource(properties = {"username=John Shepard", "password=$2y$10$zVpNcmtFnya0GIOb6v3dBeadXX.lVnO2enKc4Uh05bz2Cg63/SLnm"})

public class IntegrationProjects {


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private AbilityRepository abilitiesRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        technologyRepository.deleteAll();
        projectRepository.deleteAll();
        abilitiesRepository.deleteAll();
    }

    // GET /projects
    @Test
    public void getProjects() throws Exception {
        // Given
        var project1 = new Project();
        project1.setName("test project 1 name");
        project1.setColor("test project 1 color");
        project1.setIcon("test project 1 icon");
        project1.setDescription("test project 1 description");
        project1.setGit("test project 1 git");

        var ability = new Ability();
        ability.setImage("test ability image");
        ability.setName("test ability name");
        ability.setColor("test ability color");
        abilitiesRepository.save(ability);

        var tech = new Technology();
        tech.setName("test tech name");
        tech.setImage("test tech image");
        ability.getTechnologies().add(tech);

        technologyRepository.save(tech);

        project1.setAbilities(Set.of(ability));
        project1.setTechnologies(Set.of(tech));

        var project2 = new Project();
        project2.setName("test project 2 name");
        project2.setColor("test project 2 color");
        project2.setIcon("test project 2 icon");
        project2.setDescription("test project 2 description");
        project2.setGit("test project 2 git");

        projectRepository.save(project1);
        projectRepository.save(project2);

        // When
        mvc.perform(get("/projects")
                .contentType("application/json"))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].image", is("test project 1 image")))
                .andExpect(jsonPath("$[0].color", is("test project 1 color")))
                .andExpect(jsonPath("$[0].name", is("test project 1 name")))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].description", is("test project 1 description")))
                .andExpect(jsonPath("$[0].git", is("test project 1 git")))
                .andExpect(jsonPath("$[0].technologies", hasSize(1)))
                .andExpect(jsonPath("$[0].technologies[0].name", is("test tech name")))
                .andExpect(jsonPath("$[0].technologies[0].image", is("test tech name")))
                .andExpect(jsonPath("$[0].abilities[0].name", is("test ability name")))
                .andExpect(jsonPath("$[0].abilities[0].image", is("test ability name")))
                .andExpect(jsonPath("$[0].abilities[0].color", is("test ability color")))
                .andExpect(jsonPath("$[1].image", is("test project 2 image")))
                .andExpect(jsonPath("$[1].color", is("test project 2 color")))
                .andExpect(jsonPath("$[1].name", is("test project 2 name")))
                .andExpect(jsonPath("$[1].id", notNullValue()))
                .andExpect(jsonPath("$[1].description", is("test project 2 description")))
                .andExpect(jsonPath("$[1].git", is("test project 2 git")))
                .andExpect(jsonPath("$[1].abilities", hasSize(0)))
                .andExpect(jsonPath("$[1].technologies", hasSize(0)));
    }


    // PUT /projects/{id}
    public void testUpdateProject() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var project1 = new Project();
        project1.setName("test project 1 name");
        project1.setColor("test project 1 color");
        project1.setIcon("test project 1 icon");
        project1.setDescription("test project 1 description");
        project1.setGit("test project 1 git");

        projectRepository.save(project1);

        project1.setName("test project 1 name updated");

        // When
        mvc.perform(put("/projects/" + project1.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project1))
                .header("Authorization", token))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.image", is("test project 1 image")))
                .andExpect(jsonPath("$.color", is("test project 1 color")))
                .andExpect(jsonPath("$.name", is("test project 1 name updated")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.description", is("test project 1 description")))
                .andExpect(jsonPath("$.git", is("test project 1 git")))
                .andExpect(jsonPath("$.abilities", hasSize(0)))
                .andExpect(jsonPath("$.technologies", hasSize(0)));

        project1 = projectRepository.findById(project1.getId()).get();
        assertEquals("The project's name should have been updated",
                "test project 1 name updated", project1.getName());
    }

    // DELETE /projects/{id}
    public void deleteProject() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var project1 = new Project();
        project1.setName("test project 1 name");
        project1.setColor("test project 1 color");
        project1.setIcon("test project 1 icon");
        project1.setDescription("test project 1 description");
        project1.setGit("test project 1 git");

        projectRepository.save(project1);

        project1.setName("test project 1 name updated");

        // When
        mvc.perform(delete("/projects/" + project1.getId())
                .contentType("application/json")
                .header("Authorization", token))
                // Then
                .andExpect(status().isOk());
        assertEquals("Project should have been deleted",1L,projectRepository.count());
    }

    // POST /projects
    // POST /projects/{id}/technologies
    // POST /projects/{id}/abilities
    // DELETE /projects/{id}/technologie
    // DELETE /projects/{id}/abilities

}
