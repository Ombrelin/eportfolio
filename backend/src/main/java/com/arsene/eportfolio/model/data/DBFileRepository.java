package com.arsene.eportfolio.model.data;

import com.arsene.eportfolio.model.entities.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBFileRepository extends JpaRepository<DBFile, String> {
}
