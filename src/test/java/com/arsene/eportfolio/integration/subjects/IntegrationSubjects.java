package com.arsene.eportfolio.integration.subjects;

import com.arsene.eportfolio.EportfolioApplication;
import com.arsene.eportfolio.integration.IntegrationUtil;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.entities.Subject;
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

import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EportfolioApplication.class)
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestPropertySource(properties = {"username=John Shepard", "password=$2y$10$zVpNcmtFnya0GIOb6v3dBeadXX.lVnO2enKc4Uh05bz2Cg63/SLnm"})
public class IntegrationSubjects {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private SubjectRepository repository;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        repository.deleteAll();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createSubject_InsertsInDb() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var subject = new Subject();
        subject.setImage("test image");
        subject.setIcon("test icon");
        subject.setName("test name");

        // When
        mvc.perform(
                post("/subjects")
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(subject))
        )

        // Then
        .andExpect(status().isCreated());

        assertEquals("Database should contain exactly one subject", 1L, repository.count());

        var createdSubject = repository.findAll().get(0);

        assertEquals("Created Subject should have the right image", "test image", createdSubject.getImage());
        assertEquals("Created Subject should have the right icon", "test icon", createdSubject.getIcon());
        assertEquals("Created Subject should have the right name", "test name", createdSubject.getName());
        assertNotNull("Created Subject should have an Id", createdSubject.getId());
    }

    @Test
    public void getSubjects_RetrievesExistingSubjects() throws Exception {
        // Given
        var subject1 = new Subject();
        subject1.setImage("test image 1");
        subject1.setIcon("test icon 1");
        subject1.setName("test name 1");

        var subject2 = new Subject();
        subject2.setImage("test image 2");
        subject2.setIcon("test icon 2");
        subject2.setName("test name 2");

        repository.save(subject1);
        repository.save(subject2);

        // When
        mvc.perform(
                get("/subjects")
                        .contentType("application/json")
        )

        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].image", is("test image 1")))
        .andExpect(jsonPath("$[0].icon", is("test icon 1")))
        .andExpect(jsonPath("$[0].name", is("test name 1")))
        .andExpect(jsonPath("$[0].id", notNullValue()))
        .andExpect(jsonPath("$[1].image", is("test image 2")))
        .andExpect(jsonPath("$[1].icon", is("test icon 2")))
        .andExpect(jsonPath("$[1].name", is("test name 2")))
        .andExpect(jsonPath("$[1].id", notNullValue()));

    }

    @Test
    public void getSubject_RetrievesSubjects() throws Exception {
        // Given
        var subject1 = new Subject();
        subject1.setImage("test image 1");
        subject1.setIcon("test icon 1");
        subject1.setName("test name 1");

        repository.save(subject1);

        // When
        mvc.perform(
                get("/subjects/" + subject1.getId())
                        .contentType("application/json")
        )

        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.image", is("test image 1")))
        .andExpect(jsonPath("$.icon", is("test icon 1")))
        .andExpect(jsonPath("$.name", is("test name 1")))
        .andExpect(jsonPath("$.id", is(subject1.getId())));

    }

    @Test
    public void deleteSubject_DeletesFromDb() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var subject1 = new Subject();
        subject1.setImage("test image 1");
        subject1.setIcon("test icon 1");
        subject1.setName("test name 1");

        repository.save(subject1);

        // When
        mvc.perform(
                delete("/subjects/" + subject1.getId())
                        .header("Authorization", token)
                        .contentType("application/json")
        )

        // Then
        .andExpect(status().isOk());

        assertEquals("The subject should have been deleted", 0L, repository.count());
    }


    @Test
    public void editSubject_EditsInDb() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var subject1 = new Subject();
        subject1.setImage("test image 1");
        subject1.setIcon("test icon 1");
        subject1.setName("test name 1");

        repository.save(subject1);

        var editDto = new Subject();
        editDto.setImage("test image 1");
        editDto.setIcon("test icon 1");
        editDto.setName("test name updated");
        editDto.setId(subject1.getId());
        editDto.setAbilities(new HashSet<>());

        // When
        mvc.perform(
                put("/subjects/" + editDto.getId())
                        .header("Authorization", token)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(editDto))
        )

                // Then
                .andExpect(status().isOk());

        assertEquals("The subject should still exist", 1L, repository.count());

        subject1 = repository.findById(subject1.getId()).get();
        assertEquals("The name should have been updated", "test name updated",subject1.getName());
    }

}
