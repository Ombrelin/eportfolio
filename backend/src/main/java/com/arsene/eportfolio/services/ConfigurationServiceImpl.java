package com.arsene.eportfolio.services;

import com.arsene.eportfolio.model.data.ConfigurationRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationServiceImpl implements ConfigurationService{

    private final ConfigurationRepository configurationRepository;

    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public String getConfig(String key) {
        return null;
    }

    @Override
    public void setConfig(String key, String value) {

    }
}
