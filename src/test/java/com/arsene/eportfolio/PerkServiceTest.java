package com.arsene.eportfolio;

import com.arsene.eportfolio.model.data.PerkRepository;
import com.arsene.eportfolio.model.entities.Perk;
import com.arsene.eportfolio.services.PerkService;
import com.arsene.eportfolio.services.PerkServiceImpl;
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
public class PerkServiceTest {

    private PerkService perkService;

    @Mock
    private PerkRepository perkRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        perkService = new PerkServiceImpl(perkRepository);
    }

    @Test
    @DisplayName("Getting all perks")
    public void getPerksTest() {
        List<Perk> perksMock = new LinkedList<Perk>();
        perksMock.add(new Perk("Id1", "TestName1", "TestImage1"));
        perksMock.add(new Perk("Id2", "TestName2", "TestImage2"));
        perksMock.add(new Perk("Id3", "TestName3", "TestImage3"));
        when(perkRepository.findAll()).thenReturn(perksMock);

        List<Perk> perks = perkService.getAll();


        then(perkRepository).should().findAll();
        assertEquals(3, perks.size());
        assertEquals("Id1", perks.get(0).getId());
        assertEquals("Id2", perks.get(1).getId());
        assertEquals("Id3", perks.get(2).getId());

        assertEquals("TestName1", perks.get(0).getName());
        assertEquals("TestName2", perks.get(1).getName());
        assertEquals("TestName3", perks.get(2).getName());

        assertEquals("TestImage1", perks.get(0).getImage());
        assertEquals("TestImage2", perks.get(1).getImage());
        assertEquals("TestImage3", perks.get(2).getImage());
    }

    @Test
    @DisplayName("Getting a perk from its ID")
    public void getPerkTest() {
        Optional<Perk> perkMock = Optional.of(new Perk("Id1", "TestName1", "TestImage1"));
        when(perkRepository.findById("Id1")).thenReturn(perkMock);

        Perk perk = perkService.get("Id1");

        then(perkRepository).should().findById(any(String.class));
        assertEquals("Id1", perk.getId());
        assertEquals("TestName1", perk.getName());
        assertEquals("TestImage1", perk.getImage());
    }

    @Test
    @DisplayName("Creating a new Perk")
    public void createPerkTest() {
        Perk perkMock = new Perk("Id1", "TestName1", "TestImage1");
        given(perkRepository.save(any(Perk.class))).willReturn(perkMock);

        // When
        Perk perk = perkService.create(new Perk(null, "TestName1", "TestImage1"));

        then(perkRepository).should().save(any(Perk.class));
        assertEquals("Id1", perk.getId());
    }

    @Test
    @DisplayName("Updating a perk")
    public void updatePerkTest() {
        Perk perkMock = new Perk("Test1", "TestName1", "TestImage1");
        Perk perkMockUpdated = new Perk("Test1", "TestNameUpdated", "TestImage1");
        given(perkRepository.findById("Test1")).willReturn(Optional.of(perkMock));
        given(perkRepository.save(any(Perk.class))).willReturn(perkMockUpdated);

        // When
        Perk perk = perkService.update("Test1", perkMock);

        then(perkRepository).should().findById("Test1");
        then(perkRepository).should().save(perkMock);
        assertEquals("TestNameUpdated", perk.getName());
    }

    @Test
    @DisplayName("Delete a perk")
    public void deletePerkTest() {
        Perk perkMock = new Perk("Test1", "TestName1", "TestImage1");
        perkService.delete(perkMock.getId());

        then(perkRepository).should().deleteById(any(String.class));
    }
}
