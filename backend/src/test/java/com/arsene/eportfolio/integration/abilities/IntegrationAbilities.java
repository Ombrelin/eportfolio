package com.arsene.eportfolio.integration.abilities;

import com.arsene.eportfolio.EportfolioApplication;
import com.arsene.eportfolio.integration.IntegrationUtil;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.dtos.AbilityDto;
import com.arsene.eportfolio.model.dtos.UpdateAbilityDto;
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

import static org.hamcrest.Matchers.*;
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
public class IntegrationAbilities {


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AbilityRepository abilitiesRepository;

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        subjectRepository.deleteAll();
        abilitiesRepository.deleteAll();
        technologyRepository.deleteAll();
    }

    // POST /subjects/{subjectId}/abilities
    @Test
    //@Transactional
    public void createAbility() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var subject = new Subject("test subject name", "test subject icon", "test subject image");
        subjectRepository.save(subject);

        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);

        // When
        var result = mvc.perform(post("/subjects/" + subject.getId() + "/abilities")
                .header("Authorization", token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(ability)))

                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("test ability name")))
                .andExpect(jsonPath("$.image", is("test ability image")))
                .andExpect(jsonPath("$.color", is("test ability color")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andReturn().getResponse().getContentAsString();

        var responseDto = objectMapper.readValue(result, AbilityDto.class);

        var transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            assertEquals("One record should have been inserted", 1L, abilitiesRepository.count());
            var abilityFromDb = abilitiesRepository.findById(responseDto.getId()).get();
            assertEquals("Ability name should be correct", "test ability name", abilityFromDb.getName());
            assertEquals("Ability image should be correct", "test ability image", abilityFromDb.getImage());
            assertEquals("Ability color should be correct", "test ability color", abilityFromDb.getColor());

            var subjectFromDb = subjectRepository.findById(subject.getId()).get();
            assertEquals("Subject should have one ability", 1, subjectFromDb.getAbilities().size());

            abilityFromDb = subjectFromDb.getAbilities().stream().findFirst().get();
            assertEquals("Ability name should be correct", "test ability name", abilityFromDb.getName());
            assertEquals("Ability image should be correct", "test ability image", abilityFromDb.getImage());
            assertEquals("Ability color should be correct", "test ability color", abilityFromDb.getColor());

            return null;
        });

    }


    // PUT /subjects/{subjectId}/abilities/{abilityId}
    @Test
    public void updateAbility() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);

        var subject = new Subject("test subject name", "test subject icon", "test subject image");
        subjectRepository.save(subject);

        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);
        abilitiesRepository.save(ability);

        subject.getAbilities().add(ability);
        subjectRepository.save(subject);

        var abilityDto = new UpdateAbilityDto(ability.getId(), "test ability name updated", ability.getColor(), ability.getImage());

        // When
        mvc.perform(put("/subjects/" + subject.getId() + "/abilities/" + ability.getId())
                .header("Authorization", token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(abilityDto)))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test ability name updated")))
                .andExpect(jsonPath("$.image", is("test ability image")))
                .andExpect(jsonPath("$.color", is("test ability color")))
                .andExpect(jsonPath("$.id", is(ability.getId())));

        var transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            assertEquals("The existing record should still be there", 1L, abilitiesRepository.count());
            var abilityFromDb = abilitiesRepository.findById(ability.getId()).get();
            assertEquals("Ability name should be correct", "test ability name updated", abilityFromDb.getName());
            assertEquals("Ability image should be correct", "test ability image", abilityFromDb.getImage());
            assertEquals("Ability color should be correct", "test ability color", abilityFromDb.getColor());
            return null;
        });
    }

    // DELETE /subjects/{subjectId}/abilities/{abilityId}
    @Test
    public void deleteAbility() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);

        var subject = new Subject("test subject name", "test subject icon", "test subject image");
        subjectRepository.save(subject);

        var ability = new Ability("test ability name", "test ability color", "test ability image", subject);
        abilitiesRepository.save(ability);

        var tech = new Technology("test tech name", "test tech image", ability);
        technologyRepository.save(tech);

        // When
        mvc.perform(delete(String.format("/subjects/%d/abilities/%d", subject.getId(), ability.getId()))
                .header("Authorization", token))

                // Then
                .andExpect(status().isNoContent());

        var transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.execute(status -> {
            assertEquals("The existing record should have been deleted", 0L, abilitiesRepository.count());
            var subjectInDb = subjectRepository.findById(subject.getId()).get();
            assertEquals("Subject should have 0 ability", 0, subjectInDb.getAbilities().size());
            assertEquals("Child technology should have been deleted", 0L, technologyRepository.count());
            return null;
        });
    }

    // GET /subjects/{subjectId}/abilities/{id}
    @Test
    public void getAbility() throws Exception {
        // Given
        var subject = new Subject("test subject 1 name", "test subject 1 icon", "test subject 1 image");
        subjectRepository.save(subject);

        var ability = new Ability("test ability 1 name", "test ability 1 color", "test ability 1 image", subject);
        abilitiesRepository.save(ability);

        subject.getAbilities().add(ability);
        subjectRepository.save(subject);

        var tech = new Technology("test tech name", "test tech image", ability);
        technologyRepository.save(tech);

        // When
        mvc.perform(get("/subjects/" + subject.getId() + "/abilities/" + ability.getId())
                .contentType("application/json"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.image", is("test ability 1 image")))
                .andExpect(jsonPath("$.color", is("test ability 1 color")))
                .andExpect(jsonPath("$.name", is("test ability 1 name")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.technologies", notNullValue()))
                .andExpect(jsonPath("$.technologies", hasSize(1)))
                .andExpect(jsonPath("$.technologies[0].name", is("test tech name")))
                .andExpect(jsonPath("$.technologies[0].image", is("test tech image")))
                .andExpect(jsonPath("$.technologies[0].id", notNullValue()));
    }

    // GET /abilities
    @Test
    public void getAllAbilities() throws Exception {
        // Given
        var subject = new Subject("test subject 1 name", "test subject 1 icon", "test subject 1 image");
        subjectRepository.save(subject);

        var ability = new Ability("test ability 1 name", "test ability 1 color", "test ability 1 image", subject);
        abilitiesRepository.save(ability);

        subject.getAbilities().add(ability);
        subjectRepository.save(subject);

        var subject2 = new Subject("test subject 2 name", "test subject 2 icon", "test subject 2 image");
        subjectRepository.save(subject2);

        var ability2 = new Ability("test ability 2 name", "test ability 2 color", "test ability 2 image", subject);
        abilitiesRepository.save(ability2);

        subject2.getAbilities().add(ability2);
        subjectRepository.save(subject2);

        // When
        mvc.perform(get("/abilities")
                .contentType("application/json"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].image", is("test ability 1 image")))
                .andExpect(jsonPath("$[0].color", is("test ability 1 color")))
                .andExpect(jsonPath("$[0].name", is("test ability 1 name")))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].technologies", empty()))
                .andExpect(jsonPath("$[1].image", is("test ability 2 image")))
                .andExpect(jsonPath("$[1].color", is("test ability 2 color")))
                .andExpect(jsonPath("$[1].name", is("test ability 2 name")))
                .andExpect(jsonPath("$[1].id", notNullValue()))
                .andExpect(jsonPath("$[1].technologies", empty()));
    }

}
