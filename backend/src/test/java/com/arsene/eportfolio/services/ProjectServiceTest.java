package com.arsene.eportfolio.services;

import com.arsene.eportfolio.model.data.AbilityRepository;
import com.arsene.eportfolio.model.data.ProjectRepository;
import com.arsene.eportfolio.model.data.TechnologyRepository;
import com.arsene.eportfolio.model.dtos.UpdateProjectDto;
import com.arsene.eportfolio.model.entities.Project;
import com.arsene.eportfolio.utils.DataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.*;

@SpringJUnitConfig
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TechnologyRepository technologyRepository;

    @Mock
    private AbilityRepository abilityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("getProjects returns all projects present in the database")
    public void getProjects_returnsAllProjects() {
        // Given
        var projectService = new ProjectServiceImpl(projectRepository, abilityRepository, technologyRepository);

        List<Project> projectsData = DataFactory.createProjects(5);
        when(projectRepository.findAll()).thenReturn(projectsData);

        // When
        var projects = projectService.getProjects();

        // Then
        assertEquals("All project should be retrieved", projectsData.size(), projects.size());
    }

    @Test
    @DisplayName("createProject insert a project in the database")
    public void createProject_insertsProject() {
        // Given
        var projectService = new ProjectServiceImpl(projectRepository, abilityRepository, technologyRepository);
        var ability = DataFactory.createAbilityWithId();
        var technology = DataFactory.createTechnologyWithId(ability);
        var projectDto = DataFactory.createProjectDto(ability.getId(), technology.getId());
        List<Project> projectsData = DataFactory.createProjects(5);
        when(projectRepository.save(any(Project.class))).then(i -> {
            Project p = i.getArgument(0, Project.class);
            p.setId(1);
            projectsData.add(p);
            return p;
        });

        when(abilityRepository.findByIdIn(List.of(1))).thenReturn(List.of(ability));
        when(technologyRepository.findByIdIn(List.of(1))).thenReturn(List.of(technology));

        // When
        var project = projectService.createProject(projectDto);

        // Then
        assertNotNull("Resulting DTO should not be null", project);
        assertEquals("Resulting DTO should have correct Id", 1, project.getId());
        assertEquals("Resulting DTO should have one ability", 1, project.getAbilities().size());
        assertEquals("Resulting DTO should have one technology", 1, project.getTechnologies().size());
        assertEquals("Project should have been inserted in DB", 6, projectsData.size());
    }

    @Test
    @DisplayName("deleteProject removes the project from the database")
    public void deleteProject_deletesProject() {
        // Given
        var projectService = new ProjectServiceImpl(projectRepository, abilityRepository, technologyRepository);
        List<Project> projectsData = DataFactory.createProjectsWithId(5);

        doAnswer(i -> {
            projectsData.remove(1);
            return null;
        }).when(projectRepository).deleteById(1);

        // When
        projectService.deleteProject(1);

        // Then
        assertEquals("One project should have been deleted", 4, projectsData.size());
        assertTrue("The correct project should have been deleted", !projectsData
                .stream()
                .map(project -> project.getId())
                .collect(Collectors.toSet())
                .contains(1)
        );
    }

    @Test
    @DisplayName("updateProject update the project in the database")
    public void updateProject_UpdatesProject() {
        // Given
        var projectService = new ProjectServiceImpl(projectRepository, abilityRepository, technologyRepository);
        var project = DataFactory.createProjectWithId();
        List<Project> updatedProjects = new LinkedList<>();
        var updateProjectDto = new UpdateProjectDto(
                project.getId(),
                project.getName() + " updated",
                project.getIcon(),
                project.getDescription(),
                project.getGit(),
                project.getColor()
        );

        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).then(i -> {
            Project p = i.getArgument(0);
            updatedProjects.add(p);
            return p;
        });
        // When
        var result = projectService.updateProject(updateProjectDto);

        // Then
        assertEquals("Result should have the updated name", "test project 1 name updated", result.getName());
        assertEquals("The project should have been updated", "test project 1 name updated", updatedProjects.get(0).getName());
    }

    @Test
    @DisplayName("addAbilityToProject adds an existing ability to the project")
    public void addAbility_addsAbilityToProject() {
        // Given
        var projectService = new ProjectServiceImpl(projectRepository, abilityRepository, technologyRepository);

        var project = DataFactory.createProjectWithId();
        var ability = DataFactory.createAbilityWithId();

        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(abilityRepository.findById(1)).thenReturn(Optional.of(ability));

        List<Project> updatedProjects = new LinkedList<>();
        when(projectRepository.save(any(Project.class))).then(i -> {
            Project p = i.getArgument(0);
            updatedProjects.add(p);
            return p;
        });

        // When
        var result = projectService.addAbilityToProject(1, 1);

        // Then
        assertEquals("The result should have the added ability", 1, result.getAbilities().size());
        assertEquals("The project should have the new ability", 1, updatedProjects.get(0).getAbilities().size());
    }

    @Test
    @DisplayName("addTechnologyToProject adds an existing ability to the project")
    public void addTechnology_addsTechnologyToProject() {
        // Given
        var projectService = new ProjectServiceImpl(projectRepository, abilityRepository, technologyRepository);

        var project = DataFactory.createProjectWithId();
        var tech = DataFactory.createTechnologyWithId();

        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(technologyRepository.findById(1)).thenReturn(Optional.of(tech));

        List<Project> updatedProjects = new LinkedList<>();
        when(projectRepository.save(any(Project.class))).then(i -> {
            Project p = i.getArgument(0);
            updatedProjects.add(p);
            return p;
        });

        // When
        var result = projectService.addTechnologyToProject(1, 1);

        // Then
        assertEquals("The result should have the added technology", 1, result.getTechnologies().size());
        assertEquals("The project should have the new technology", 1, updatedProjects.get(0).getTechnologies().size());
    }

    @Test
    @DisplayName("removeTechnologyFromProject removes a technology from the project")
    public void removeTechnology_removeTechnologyFromProject() {
        // Given
        var projectService = new ProjectServiceImpl(projectRepository, abilityRepository, technologyRepository);

        var project = DataFactory.createProjectWithId();
        var tech = DataFactory.createTechnologyWithId();
        project.getTechnologies().add(tech);

        List<Project> updatedProjects = new LinkedList<>();
        when(projectRepository.save(any(Project.class))).then(i -> {
            Project p = i.getArgument(0);
            updatedProjects.add(p);
            return p;
        });
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        // When
        projectService.removeTechnologyFromProject(1, 1);

        // Then
        assertEquals("The technology should have been removed from the project", 0, updatedProjects.get(0).getTechnologies().size());
    }

    @Test
    @DisplayName("removeAbilityFromProject removes an ability from the project")
    public void removeAbility_removeAbilityFromProject() {
        // Given
        var projectService = new ProjectServiceImpl(projectRepository, abilityRepository, technologyRepository);

        var project = DataFactory.createProjectWithId();
        var ability = DataFactory.createAbilityWithId();
        project.getAbilities().add(ability);

        List<Project> updatedProjects = new LinkedList<>();
        when(projectRepository.save(any(Project.class))).then(i -> {
            Project p = i.getArgument(0);
            updatedProjects.add(p);
            return p;
        });
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        // When
        projectService.removeAbilityFromProject(1, 1);

        // Then
        assertEquals("The ability should have been removed from the project", 0, updatedProjects.get(0).getAbilities().size());

    }
}
