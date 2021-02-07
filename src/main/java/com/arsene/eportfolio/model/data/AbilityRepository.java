package com.arsene.eportfolio.model.data;

import com.arsene.eportfolio.model.entities.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AbilityRepository extends JpaRepository<Ability, Integer> {

}