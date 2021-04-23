package com.arsene.eportfolio.model.data;

import com.arsene.eportfolio.model.entities.Config;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigurationRepository extends JpaRepository<Config, Integer> {

    Optional<Config> getByKey(String key);

}
