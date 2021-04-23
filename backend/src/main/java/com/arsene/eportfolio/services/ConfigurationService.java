package com.arsene.eportfolio.services;

public interface ConfigurationService {

    String getConfig(String key);
    void setConfig(String key, String value);

}
