package com.arsene.eportfolio.services;

import com.arsene.eportfolio.model.data.ConfigurationRepository;
import com.arsene.eportfolio.model.entities.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringJUnitConfig
public class ConfigServiceTest {

    @Mock
    private ConfigurationRepository configurationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DisplayName("getConfig returns the value present in the database")
    public void getConfig_returnsValue() {
        // Given
        var key = "name";
        var value = "John Shepard";

        when(configurationRepository.getByKey(key)).thenReturn(Optional.of(new Config(key, value)));
        ConfigurationService service = new ConfigurationServiceImpl(configurationRepository);

        // When
        var result = service.getConfig(key);

        // Then
        assertEquals("The value should be valid", value, result);
    }

    @Test
    @DisplayName("setConfig sets the value in the database if not present")
    public void setConfig_setsValueInDb() {

    }

    @Test
    @DisplayName("setConfig sets the value in the database if not present")
    public void setConfig_setsValueInDb() {

    }
}
