package com.arsene.eportfolio.model.data;

import com.arsene.eportfolio.model.entities.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project,Integer>{}