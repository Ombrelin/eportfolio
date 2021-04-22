package com.arsene.eportfolio.model.data;

import com.arsene.eportfolio.model.entities.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AbilityRepository extends JpaRepository<Ability, Integer> {

    @Query("SELECT a FROM Ability a WHERE a.subject.id = :subjectId AND a.id = :abilityId")
    Optional<Ability> getAbilityBySubjectId(@Param("subjectId") Integer subjectId, @Param("abilityId") Integer abilityId);

    @Query("SELECT CASE WHEN (COUNT(a) > 0) THEN TRUE ELSE FALSE END " +
            "FROM Ability a " +
            "WHERE a.subject.id = :subjectId AND a.id = :abilityId")
    boolean existsAbilityBySubjectId(@Param("subjectId") Integer subjectId, @Param("abilityId") Integer abilityId);

    List<Ability> findByIdIn(List<Integer> ids);

    List<Ability> findByOrderByIdAsc();
}