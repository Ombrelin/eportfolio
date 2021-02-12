package com.arsene.eportfolio.integration.technologies;

import com.arsene.eportfolio.EportfolioApplication;
import com.arsene.eportfolio.integration.IntegrationUtil;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.dtos.CreateTechnologyDto;
import com.arsene.eportfolio.model.dtos.TechnologyDto;
import com.arsene.eportfolio.model.entities.Ability;
import com.arsene.eportfolio.model.entities.Subject;
import com.arsene.eportfolio.model.entities.Technology;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
public class IntegrationTechnologies {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AbilityRepository abilitiesRepository;

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

        technologyRepository.deleteAll();
        subjectRepository.deleteAll();
        abilitiesRepository.deleteAll();
    }

    // PUT /subjects/{subjectId}/abilities/{abilityId}/technologies/{techId}
    @Test
    public void updateTechnology() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var subject = new Subject("test subject name", "test subject icon", "test subject image");
        subjectRepository.save(subject);

        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);
        abilitiesRepository.save(ability);

        var technology = new Technology("test technology name", "test technology image", ability);
        technologyRepository.save(technology);

        var dto = new TechnologyDto(technology);
        dto.setName("test technology name updated");

        // When
        mvc.perform(put("/subjects/" + subject.getId() + "/abilities/" + ability.getId() + "/technologies/" + technology.getId())
                .header("Authorization", token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test technology name updated")))
                .andExpect(jsonPath("$.image", is("test technology image")))
                .andExpect(jsonPath("$.id", notNullValue()));

        var transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            assertEquals("The record should still be there", 1L, technologyRepository.count());
            var technologyDb = technologyRepository.findById(technology.getId()).get();
            assertEquals("Technology name should be correct", "test technology name updated", technologyDb.getName());
            assertEquals("Technology image should be correct", "test technology image", technologyDb.getImage());

            var abilityDb = abilitiesRepository.findById(ability.getId()).get();
            assertEquals("Technology should have been added to the ability", 1L, technologyRepository.count());
            var technologyFromAbilityDb = abilityDb.getTechnologies().stream().findFirst().get();
            assertEquals("Technology name should be correct", "test technology name updated", technologyFromAbilityDb.getName());
            assertEquals("Technology image should be correct", "test technology image", technologyFromAbilityDb.getImage());
            return null;
        });
    }

    // POST /subjects/{subjectId}/abilities/{abilityId}/technologies
    @Test
    public void createTechnology() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var subject = new Subject("test subject name", "test subject icon", "test subject image");
        subjectRepository.save(subject);

        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);
        abilitiesRepository.save(ability);

        var technologyDto = new CreateTechnologyDto("test technology name", "test technology image");

        // When
        String response = mvc.perform(post("/subjects/" + subject.getId() + "/abilities/" + ability.getId() + "/technologies")
                .header("Authorization", token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(technologyDto)))
                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("test technology name")))
                .andExpect(jsonPath("$.image", is("test technology image")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var resultTech = objectMapper.readValue(response, TechnologyDto.class);

        assertEquals("One record should have been inserted", 1L, technologyRepository.count());
        var technology = technologyRepository.findById(resultTech.getId()).get();
        assertEquals("Technology name should be correct", "test technology name", technology.getName());
        assertEquals("Technology image should be correct", "test technology image", technology.getImage());
    }

    // DELETE /subjects/{subjectId}/abilities/{abilityId}/technologies/{techId}
    @Test
    public void deleteTechnology() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var subject = new Subject("test subject name", "test subject icon", "test subject image");
        subjectRepository.save(subject);

        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);
        abilitiesRepository.save(ability);

        var technology = new Technology("test technology name", "test technology image", ability);
        technologyRepository.save(technology);

        // When
        mvc.perform(delete("/subjects/" + subject.getId() + "/abilities/" + ability.getId() + "/technologies/" + technology.getId())
                .header("Authorization", token))
                // Then
                .andExpect(status().isNoContent());

        var transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            assertEquals("The record should have been deleted", 0L, technologyRepository.count());
            return null;
        });
    }

    // GET /technologies
    @Test
    public void getTechnologies() throws Exception {
        // Given
        var subject = new Subject("test subject name", "test subject icon", "test subject image");
        subjectRepository.save(subject);

        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);
        abilitiesRepository.save(ability);

        var technology = new Technology("test technology name 1", "test technology image 1", ability);
        technologyRepository.save(technology);

        var technology2 = new Technology("test technology name 2", "test technology image 2", ability);
        technologyRepository.save(technology2);


        // When
        mvc.perform(get("/technologies")
                .contentType("application/json"))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].image", is("test technology image 1")))
                .andExpect(jsonPath("$[0].name", is("test technology name 1")))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[1].image", is("test technology image 2")))
                .andExpect(jsonPath("$[1].name", is("test technology name 2")))
                .andExpect(jsonPath("$[1].id", notNullValue()));
    }
}
