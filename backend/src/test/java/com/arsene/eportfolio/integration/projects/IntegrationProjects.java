package com.arsene.eportfolio.integration.projects;

import com.arsene.eportfolio.EportfolioApplication;
import com.arsene.eportfolio.integration.IntegrationUtil;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.ProjectRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Project;
import com.arsene.eportfolio.model.entities.Subject;
import com.arsene.eportfolio.model.entities.Technology;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EportfolioApplication.class)
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestPropertySource(properties = {"username=John Shepard", "password=$2y$10$zVpNcmtFnya0GIOb6v3dBeadXX.lVnO2enKc4Uh05bz2Cg63/SLnm"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IntegrationProjects {


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private AbilityRepository abilitiesRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();


        projectRepository.deleteAll();
        abilitiesRepository.deleteAll();
        technologyRepository.deleteAll();
    }

    // GET /projects
    @Test
    public void getProjects_returnsProjects() throws Exception {
        // Given
        var project1 = new Project("test project 1 name", "test project 1 icon", "test project 1 description", "test project 1 git", "test project 1 color");

        var subject = new Subject("test name", "test icon", "test image");
        subjectRepository.save(subject);
        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);
        abilitiesRepository.save(ability);
        var tech = new Technology("test tech name", "test tech image", ability);
        technologyRepository.save(tech);

        project1.setAbilities(Set.of(ability));
        project1.setTechnologies(Set.of(tech));

        var project2 = new Project("test project 2 name", "test project 2 icon", "test project 2 description", "test project 2 git", "test project 2 color");
        projectRepository.save(project1);
        projectRepository.save(project2);

        // When
        mvc.perform(get("/projects")
                .contentType("application/json"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].icon", is("test project 1 icon")))
                .andExpect(jsonPath("$[0].color", is("test project 1 color")))
                .andExpect(jsonPath("$[0].name", is("test project 1 name")))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].description", is("test project 1 description")))
                .andExpect(jsonPath("$[0].git", is("test project 1 git")))
                .andExpect(jsonPath("$[0].technologies", hasSize(1)))
                .andExpect(jsonPath("$[0].technologies[0].name", is("test tech name")))
                .andExpect(jsonPath("$[0].technologies[0].image", is("test tech image")))
                .andExpect(jsonPath("$[0].abilities[0].name", is("test ability name")))
                .andExpect(jsonPath("$[0].abilities[0].image", is("test ability image")))
                .andExpect(jsonPath("$[0].abilities[0].color", is("test ability color")))
                .andExpect(jsonPath("$[1].icon", is("test project 2 icon")))
                .andExpect(jsonPath("$[1].color", is("test project 2 color")))
                .andExpect(jsonPath("$[1].name", is("test project 2 name")))
                .andExpect(jsonPath("$[1].id", notNullValue()))
                .andExpect(jsonPath("$[1].description", is("test project 2 description")))
                .andExpect(jsonPath("$[1].git", is("test project 2 git")))
                .andExpect(jsonPath("$[1].abilities", hasSize(0)))
                .andExpect(jsonPath("$[1].technologies", hasSize(0)));
    }


    // PUT /projects/{id}
    @Test
    public void updateProject_UpdatesProject() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var project1 = new Project("test project 1 name", "test project 1 icon", "test project 1 description", "test project 1 git", "test project 1 color");
        projectRepository.save(project1);

        project1.setName("test project 1 name updated");

        // When
        mvc.perform(put("/projects/" + project1.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project1))
                .header("Authorization", token))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.icon", is("test project 1 icon")))
                .andExpect(jsonPath("$.color", is("test project 1 color")))
                .andExpect(jsonPath("$.name", is("test project 1 name updated")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.description", is("test project 1 description")))
                .andExpect(jsonPath("$.git", is("test project 1 git")))
                .andExpect(jsonPath("$.abilities", hasSize(0)))
                .andExpect(jsonPath("$.technologies", hasSize(0)));

        project1 = projectRepository.findById(project1.getId()).get();
        assertEquals("The project's name should have been updated", "test project 1 name updated", project1.getName());
    }

    // DELETE /projects/{id}
    @Test
    public void deleteProject_deletesProject() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var project1 = new Project("test project 1 name", "test project 1 icon", "test project 1 description", "test project 1 git", "test project 1 color");
        projectRepository.save(project1);

        // When
        mvc.perform(delete("/projects/" + project1.getId())
                .contentType("application/json")
                .header("Authorization", token))
                // Then
                .andExpect(status().isOk());

        assertEquals("Project should have been deleted", 0L, projectRepository.count());
    }

    // POST /projects
    @Test
    public void createProject_createsProject() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var project1 = new Project("test project 1 name", "test project 1 icon", "test project 1 description", "test project 1 git", "test project 1 color");

        // When
        var response = mvc.perform(post("/projects")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project1))
                .header("Authorization", token))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.icon", is("test project 1 icon")))
                .andExpect(jsonPath("$.color", is("test project 1 color")))
                .andExpect(jsonPath("$.name", is("test project 1 name")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.description", is("test project 1 description")))
                .andExpect(jsonPath("$.git", is("test project 1 git")))
                .andExpect(jsonPath("$.abilities", hasSize(0)))
                .andExpect(jsonPath("$.technologies", hasSize(0)))
                .andReturn()
                .getResponse();

        var project = objectMapper.readValue(response.getContentAsString(), Project.class);

        assertEquals("One project should have been created", 1L, projectRepository.count());
        project1 = projectRepository.findById(project.getId()).get();
        assertEquals("Project in DB should have correct image", "test project 1 icon", project1.getIcon());
        assertEquals("Project in DB should have correct image", "test project 1 name", project1.getName());
        assertEquals("Project in DB should have correct image", "test project 1 color", project1.getColor());
        assertEquals("Project in DB should have correct image", "test project 1 git", project1.getGit());
        assertEquals("Project in DB should have correct image", "test project 1 description", project1.getDescription());
    }

    // PUT /projects/{id}/technologies/{techId}
    @Test
    public void addTechnology() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var project1 = new Project("test project 1 name", "test project 1 icon", "test project 1 description", "test project 1 git", "test project 1 color");
        projectRepository.save(project1);

        var subject = new Subject("test name", "test icon", "test image");
        subjectRepository.save(subject);
        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);
        abilitiesRepository.save(ability);
        var tech = new Technology("test tech name", "test tech image", ability);
        technologyRepository.save(tech);

        // When
        mvc.perform(put(String.format("/projects/%s/technologies/%s", project1.getId(), tech.getId()))
                .contentType("application/json")
                .header("Authorization", token))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.icon", is("test project 1 icon")))
                .andExpect(jsonPath("$.color", is("test project 1 color")))
                .andExpect(jsonPath("$.name", is("test project 1 name")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.description", is("test project 1 description")))
                .andExpect(jsonPath("$.git", is("test project 1 git")))
                .andExpect(jsonPath("$.abilities", hasSize(0)))
                .andExpect(jsonPath("$.technologies", hasSize(1)))
                .andExpect(jsonPath("$.technologies[0].name", is("test tech name")))
                .andExpect(jsonPath("$.technologies[0].image", is("test tech image")));

        var transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            assertEquals("Project should still exist", 1L, projectRepository.count());
            var projectDb = projectRepository.findById(project1.getId()).get();
            assertEquals("Project in DB should have correct image", "test project 1 icon", projectDb.getIcon());
            assertEquals("Project in DB should have correct name", "test project 1 name", projectDb.getName());
            assertEquals("Project in DB should have correct color", "test project 1 color", projectDb.getColor());
            assertEquals("Project in DB should have correct git", "test project 1 git", projectDb.getGit());
            assertEquals("Project in DB should have correct description", "test project 1 description", projectDb.getDescription());
            assertEquals("Project in DB should have correct child techs", 1, projectDb.getTechnologies().size());
            assertEquals("Project should have one related technology", 1, projectDb.getTechnologies().size());
            var techDb = projectDb.getTechnologies().stream().findFirst().get();
            assertEquals("Child technology should have correct test tech name", "test tech name", techDb.getName());
            assertEquals("Child technology should have correct test tech image", "test tech image", techDb.getImage());
            return null;
        });
    }

    // PUT /projects/{id}/abilities/{abilityId}
    @Test
    public void addAbility() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var project1 = new Project("test project 1 name", "test project 1 icon", "test project 1 description", "test project 1 git", "test project 1 color");
        projectRepository.save(project1);

        var subject = new Subject("test name", "test icon", "test image");
        subjectRepository.save(subject);
        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);
        abilitiesRepository.save(ability);
        var tech = new Technology("test tech name", "test tech image", ability);
        technologyRepository.save(tech);

        // When
        mvc.perform(put(String.format("/projects/%s/abilities/%s", project1.getId(), ability.getId()))
                .contentType("application/json")
                .header("Authorization", token))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.icon", is("test project 1 icon")))
                .andExpect(jsonPath("$.color", is("test project 1 color")))
                .andExpect(jsonPath("$.name", is("test project 1 name")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.description", is("test project 1 description")))
                .andExpect(jsonPath("$.git", is("test project 1 git")))
                .andExpect(jsonPath("$.abilities", hasSize(1)))
                .andExpect(jsonPath("$.abilities[0].name", is("test ability name")))
                .andExpect(jsonPath("$.abilities[0].image", is("test ability image")))
                .andExpect(jsonPath("$.abilities[0].color", is("test ability color")));

        var transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            assertEquals("Project should still exist", 1L, projectRepository.count());

            var projectDb = projectRepository.findById(project1.getId()).get();
            assertEquals("Project in DB should have correct image", "test project 1 icon", projectDb.getIcon());
            assertEquals("Project in DB should have correct name", "test project 1 name", projectDb.getName());
            assertEquals("Project in DB should have correct color", "test project 1 color", projectDb.getColor());
            assertEquals("Project in DB should have correct git", "test project 1 git", projectDb.getGit());
            assertEquals("Project in DB should have correct description", "test project 1 description", projectDb.getDescription());
            assertEquals("Project in DB should have correct child techs", 1, projectDb.getAbilities().size());
            assertEquals("Project should have one ability", 1, projectDb.getAbilities().size());
            var abilityDb = projectDb.getAbilities().stream().findFirst().get();
            assertEquals("Child technology should have correct test tech name", "test ability name", abilityDb.getName());
            assertEquals("Child technology should have correct test tech image", "test ability image", abilityDb.getImage());
            assertEquals("Child technology should have correct test tech color", "test ability color", abilityDb.getColor());
            return null;
        });
    }

    // DELETE /projects/{id}/technology/{techId}
    @Test
    public void removeTech() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);

        var subject = new Subject("test name", "test icon", "test image");
        subjectRepository.save(subject);
        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);
        abilitiesRepository.save(ability);
        var tech = new Technology("test tech name", "test tech image", ability);
        technologyRepository.save(tech);

        var project1 = new Project("test project 1 name", "test project 1 icon", "test project 1 description", "test project 1 git", "test project 1 color");
        project1.setTechnologies(Set.of(tech));
        projectRepository.save(project1);

        // When
        mvc.perform(delete(String.format("/projects/%s/technologies/%s", project1.getId(), tech.getId()))
                .contentType("application/json")
                .header("Authorization", token))
                // Then
                .andExpect(status().isNoContent());

        var transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            var projectDb = projectRepository.findById(project1.getId()).get();
            assertEquals("Tech should have been removed from project", 0, projectDb.getTechnologies().size());
            assertEquals("Tech should not have been deleted", 1L, technologyRepository.count());
            return null;
        });
    }

    // DELETE /projects/{id}/abilities/{abilityId}
    @Test
    public void removeAbility() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);

        var subject = new Subject("test name", "test icon", "test image");
        subjectRepository.save(subject);
        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);
        abilitiesRepository.save(ability);
        var tech = new Technology("test tech name", "test tech image", ability);
        technologyRepository.save(tech);

        var project1 = new Project("test project 1 name", "test project 1 icon", "test project 1 description", "test project 1 git", "test project 1 color");
        project1.setAbilities(Set.of(ability));
        projectRepository.save(project1);


        // When
        mvc.perform(delete(String.format("/projects/%s/abilities/%s", project1.getId(), ability.getId()))
                .contentType("application/json")
                .header("Authorization", token))
                // Then
                .andExpect(status().isNoContent());

        var transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            var projectDb = projectRepository.findById(project1.getId()).get();
            assertEquals("Tech should have been removed from project", 0, projectDb.getAbilities().size());
            assertEquals("Ability should not have been deleted", 1L, abilitiesRepository.count());
            return null;
        });
    }

}
