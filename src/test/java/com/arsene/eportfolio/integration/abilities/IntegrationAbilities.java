package com.arsene.eportfolio.integration.abilities;

import com.arsene.eportfolio.EportfolioApplication;
import com.arsene.eportfolio.integration.IntegrationUtil;
import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.SubjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
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

import java.util.HashSet;

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

    // POST /subjects/{subjectId}/abilities
    @Test
    public void createAbility() throws Exception{
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var subject = new Subject();
        subject.setImage("test subject image");
        subject.setIcon("test subject icon");
        subject.setName("test subject name");
        subject.setAbilities(new HashSet<>() {});

        subjectRepository.save(subject);

        var ability = new Ability();
        ability.setImage("test ability image");
        ability.setName("test ability name");
        ability.setColor("test ability color");

        // When
        mvc.perform(post("/subjects/" + subject.getId() + "/abilities")
                .header("Authorization", token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(ability)))

        // Then
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is("test ability name")))
        .andExpect(jsonPath("$.image", is("test ability image")))
        .andExpect(jsonPath("$.color", is("test ability color")))
        .andExpect(jsonPath("$.id", notNullValue()));

        assertEquals("One record should have been inserted", 1L, abilitiesRepository.count());
        ability = abilitiesRepository.findById(ability.getId()).get();
        assertEquals("Ability name should be correct", "test ability name", ability.getName());
        assertEquals("Ability image should be correct", "test ability image", ability.getImage());
        assertEquals("Ability color should be correct", "test ability color", ability.getColor());

        subject = subjectRepository.findById(subject.getId()).get();
        assertEquals("Subject should have one ability", 1, subject.getAbilities().size());

        ability = subject.getAbilities().stream().findFirst().get();
        assertEquals("Ability name should be correct", "test ability name", ability.getName());
        assertEquals("Ability image should be correct", "test ability image", ability.getImage());
        assertEquals("Ability color should be correct", "test ability color", ability.getColor());
    }

    // PUT /subjects/{subjectId}/abilities/{abilityId}
    @Test
    public void updateAbility() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var subject = new Subject();
        subject.setImage("test subject image");
        subject.setIcon("test subject icon");
        subject.setName("test subject name");
        subject.setAbilities(new HashSet<>() {});

        subjectRepository.save(subject);

        var ability = new Ability();
        ability.setImage("test ability image");
        ability.setName("test ability name");
        ability.setColor("test ability color");
        subject.getAbilities().add(ability);

        abilitiesRepository.save(ability);

        ability.setName("test ability updated");

        // When
        mvc.perform(put("/subjects/" + subject.getId() + "/abilities/" + ability.getId())
                .header("Authorization", token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(ability)))

        // Then
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test ability name updated")))
                .andExpect(jsonPath("$.image", is("test ability image")))
                .andExpect(jsonPath("$.color", is("test ability color")))
                .andExpect(jsonPath("$.id", is(ability.getId())));

        assertEquals("The existing record should still be there", 1L, abilitiesRepository.count());
        ability = abilitiesRepository.findById(ability.getId()).get();
        assertEquals("Ability name should be correct", "test ability name updated", ability.getName());
        assertEquals("Ability image should be correct", "test ability image", ability.getImage());
        assertEquals("Ability color should be correct", "test ability color", ability.getColor());

    }

    // DELETE /subjects/{subjectId}/abilities/{abilityId}
    @Test
    public void deleteAbility() throws Exception {
        // Given
        var token = IntegrationUtil.login(mvc, objectMapper);
        var subject = new Subject();
        subject.setImage("test subject image");
        subject.setIcon("test subject icon");
        subject.setName("test subject name");
        subject.setAbilities(new HashSet<>() {});

        subjectRepository.save(subject);

        var ability = new Ability();
        ability.setImage("test ability image");
        ability.setName("test ability name");
        ability.setColor("test ability color");
        subject.getAbilities().add(ability);

        abilitiesRepository.save(ability);

        // When
        mvc.perform(delete("/subjects/" + subject.getId() + "/abilities"+ ability.getId())
                .header("Authorization", token)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(ability)))

                // Then
                .andExpect(status().isNoContent());

        assertEquals("The existing record should have been deleted", 0L, abilitiesRepository.count());
        subject = subjectRepository.findById(subject.getId()).get();
        assertEquals("Subject should have 0 ability", 0, subject.getAbilities().size());
    }

    // GET /abilities
    @Test
    public void getAllAbilties() throws Exception {
        // Given
        var subject = new Subject();
        subject.setImage("test subject 1 image");
        subject.setIcon("test subject 1 icon");
        subject.setName("test subject 1 name");
        subject.setAbilities(new HashSet<>() {});

        subjectRepository.save(subject);

        var ability = new Ability();
        ability.setImage("test ability 1 image");
        ability.setName("test ability 1 name");
        ability.setColor("test ability 1 color");
        subject.getAbilities().add(ability);

        abilitiesRepository.save(ability);

        var subject2 = new Subject();
        subject.setImage("test subject 2 image");
        subject.setIcon("test subject 2 icon");
        subject.setName("test subject 2 name");
        subject.setAbilities(new HashSet<>() {});

        subjectRepository.save(subject2);

        var ability2 = new Ability();
        ability.setImage("test ability 2 image");
        ability.setName("test ability 2 name");
        ability.setColor("test ability 2 color");
        subject.getAbilities().add(ability);

        abilitiesRepository.save(ability2);

        // When
        mvc.perform(get("/abilities")
                .contentType("application/json"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].image", is("test ability 1 image")))
                .andExpect(jsonPath("$[0].color", is("test ability 1 color")))
                .andExpect(jsonPath("$[0].name", is("test ability name 1")))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[1].image", is("test ability 2 image")))
                .andExpect(jsonPath("$[1].color", is("test ability 2 color")))
                .andExpect(jsonPath("$[1].name", is("test ability 2 name")))
                .andExpect(jsonPath("$[1].id", notNullValue()));
    }

}
