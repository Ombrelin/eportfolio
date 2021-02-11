package com.arsene.eportfolio.integration.subjects;

import com.arsene.eportfolio.EportfolioApplication;
import com.arsene.eportfolio.integration.IntegrationUtil;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.dtos.UpdateSubjectDto;
import com.arsene.eportfolio.model.entities.Ability;
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

    @Autowired
    private AbilityRepository abilitiesRepository;

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
        var subject = new Subject("test name", "test icon", "test image");

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


        var subject1 = new Subject("test name 1", "test icon 1", "test image 1");
        repository.save(subject1);

        var ability = new Ability("test ability 1 name", "test ability 1 color", "test ability 1 image", subject1);
        abilitiesRepository.save(ability);

        subject1.getAbilities().add(ability);

        var subject2 = new Subject("test name 2", "test icon 2", "test image 2");

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
                .andExpect(jsonPath("$[0].abilities", hasSize(1)))
                .andExpect(jsonPath("$[0].abilities[0].name", is("test ability 1 name")))
                .andExpect(jsonPath("$[0].abilities[0].image", is("test ability 1 image")))
                .andExpect(jsonPath("$[0].abilities[0].color", is("test ability 1 color")))
                .andExpect(jsonPath("$[0].abilities[0].id", notNullValue()))
                .andExpect(jsonPath("$[1].image", is("test image 2")))
                .andExpect(jsonPath("$[1].icon", is("test icon 2")))
                .andExpect(jsonPath("$[1].name", is("test name 2")))
                .andExpect(jsonPath("$[1].id", notNullValue()));

    }

    @Test
    public void deleteSubject_DeletesFromDb() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);


        var subject1 = new Subject("test name 1", "test icon 1", "test image 1");
        repository.save(subject1);

        var ability = new Ability("test ability 1 name", "test ability 1 color", "test ability 1 image", subject1);
        abilitiesRepository.save(ability);

        // When
        mvc.perform(
                delete("/subjects/" + subject1.getId())
                        .header("Authorization", token)
                        .contentType("application/json")
        )

                // Then
                .andExpect(status().isNoContent());

        assertEquals("The subject should have been deleted", 0L, repository.count());
        assertEquals("The ability should have been deleted", 0L, abilitiesRepository.count());
    }


    @Test
    public void editSubject_EditsInDb() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);

        var subject1 = new Subject("test name 1", "test icon 1", "test image 1");
        repository.save(subject1);

        var editDto = new UpdateSubjectDto(subject1.getId(), "test name updated", "test icon 1", "test image 1");

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
        assertEquals("The name should have been updated", "test name updated", subject1.getName());
    }

}
