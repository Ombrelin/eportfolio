package com.arsene.eportfolio.model.data;

import com.arsene.eportfolio.model.entities.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TechnologyRepository extends JpaRepository<Technology, Integer> {

    @Query("SELECT t " +
            "FROM Technology t " +
            "WHERE t.ability.id = :abilityId " +
            "AND t.ability.subject.id = :subjectId AND t.id = :technologyId")
    Optional<Technology> findBySubjectAndAbility(@Param("subjectId") Integer subjectId,
                                                 @Param("abilityId") Integer abilityId,
                                                 @Param("technologyId") Integer technologyId);

}