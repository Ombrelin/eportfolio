package com.arsene.eportfolio.model.data;

import com.arsene.eportfolio.model.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String> {
    User findByUsername(String username);
}
