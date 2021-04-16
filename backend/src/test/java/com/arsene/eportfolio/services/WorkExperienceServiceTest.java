package com.arsene.eportfolio.services;

import com.arsene.eportfolio.model.data.WorkExperienceRepository;
import com.arsene.eportfolio.model.entities.WorkExperience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class WorkExperienceServiceTest {

    private WorkExperienceService workExperienceService;

    @Mock
    private WorkExperienceRepository workExperienceRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        workExperienceService = new WorkExperienceServiceImpl(workExperienceRepository);
    }

    @Test
    @DisplayName("Getting all WorkExperiences")
    public void getWorkExperiencesTest() {
        List<WorkExperience> WorkExperienceMock = new LinkedList<WorkExperience>();
        WorkExperienceMock.add(new WorkExperience("Id1", "TestPosition1", "TestCompany1", "TestDescription1", 2010, 2011));
        WorkExperienceMock.add(new WorkExperience("Id2", "TestPosition2", "TestCompany2", "TestDescription2", 2020, 2021));
        WorkExperienceMock.add(new WorkExperience("Id3", "TestPosition3", "TestCompany3", "TestDescription3", 2030, 2031));

        when(workExperienceRepository.findAll()).thenReturn(WorkExperienceMock);

        List<WorkExperience> WorkExperiences = workExperienceService.getAll();


        then(workExperienceRepository).should().findAll();
        assertEquals(3, WorkExperiences.size());
        assertEquals("Id1", WorkExperiences.get(0).getId());
        assertEquals("Id2", WorkExperiences.get(1).getId());
        assertEquals("Id3", WorkExperiences.get(2).getId());

        assertEquals("TestPosition1", WorkExperiences.get(0).getPosition());
        assertEquals("TestPosition2", WorkExperiences.get(1).getPosition());
        assertEquals("TestPosition3", WorkExperiences.get(2).getPosition());

        assertEquals("TestCompany1", WorkExperiences.get(0).getCompany());
        assertEquals("TestCompany2", WorkExperiences.get(1).getCompany());
        assertEquals("TestCompany3", WorkExperiences.get(2).getCompany());

        assertEquals("TestDescription1", WorkExperiences.get(0).getDescription());
        assertEquals("TestDescription2", WorkExperiences.get(1).getDescription());
        assertEquals("TestDescription3", WorkExperiences.get(2).getDescription());

        assertEquals(2010, WorkExperiences.get(0).getStartYear());
        assertEquals(2020, WorkExperiences.get(1).getStartYear());
        assertEquals(2030, WorkExperiences.get(2).getStartYear());

        assertEquals(2011, WorkExperiences.get(0).getEndYear());
        assertEquals(2021, WorkExperiences.get(1).getEndYear());
        assertEquals(2031, WorkExperiences.get(2).getEndYear());
    }

    @Test
    @DisplayName("Getting a WorkExperience from its ID")
    public void getWorkExperienceTest() {
        Optional<WorkExperience> workExperienceMock = Optional.of(new WorkExperience("Id1", "TestPosition1", "TestCompany1", "TestDescription1", 2010, 2011));
        when(workExperienceRepository.findById("Id1")).thenReturn(workExperienceMock);

        WorkExperience workExperience = workExperienceService.get("Id1");

        then(workExperienceRepository).should().findById(any(String.class));
        assertEquals("Id1", workExperience.getId());
        assertEquals("TestPosition1", workExperience.getPosition());
        assertEquals("TestCompany1", workExperience.getCompany());
        assertEquals("TestDescription1", workExperience.getDescription());
        assertEquals(2010, workExperience.getStartYear());
        assertEquals(2011, workExperience.getEndYear());
    }

    @Test
    @DisplayName("Creating a new WorkExperience")
    public void createWorkExperienceTest() {
        WorkExperience workExperienceMock = new WorkExperience("Id1", "TestPosition1", "TestCompany1", "TestDescription1", 2010, 2011);
        given(workExperienceRepository.save(any(WorkExperience.class))).willReturn(workExperienceMock);

        // When
        WorkExperience workExperience = workExperienceService.create(new WorkExperience(null, "TestPosition1", "TestCompany1", "TestDescription1", 2010, 2011));

        then(workExperienceRepository).should().save(any(WorkExperience.class));
        assertEquals("Id1", workExperience.getId());
    }

    @Test
    @DisplayName("Updating a WorkExperience")
    public void updateWorkExperienceTest() {
        WorkExperience workExperienceMock = new WorkExperience("Id1", "TestPosition1", "TestCompany1", "TestDescription1", 2010, 2011);
        WorkExperience workExperienceMockUpdated = new WorkExperience("Id1", "TestPositionUpdated1", "TestCompany1", "TestDescription1", 2010, 2011);
        given(workExperienceRepository.findById("Id1")).willReturn(Optional.of(workExperienceMock));
        given(workExperienceRepository.save(any(WorkExperience.class))).willReturn(workExperienceMockUpdated);

        // When
        WorkExperience workExperience = workExperienceService.update("Id1", workExperienceMock);

        then(workExperienceRepository).should().findById("Id1");
        then(workExperienceRepository).should().save(workExperienceMock);
        assertEquals("TestPositionUpdated1", workExperience.getPosition());
    }

    @Test
    @DisplayName("Delete a WorkExperience")
    public void deleteWorkExperienceTest() {
        WorkExperience workExperienceMock = new WorkExperience("Id1", "TestPosition1", "TestCompany1", "TestDescription1", 2010, 2011);
        workExperienceService.delete(workExperienceMock.getId());

        then(workExperienceRepository).should().deleteById(any(String.class));
    }
}
