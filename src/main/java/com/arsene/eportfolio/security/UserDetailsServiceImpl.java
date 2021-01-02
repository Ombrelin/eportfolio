package com.arsene.eportfolio.security;

import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Environment env;

    public UserDetailsServiceImpl(Environment env) {
        this.env = env;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String usernameEnv = env.getProperty("username");
        String passwordEnv = env.getProperty("password");

        if (!Objects.equals(usernameEnv, username)) {
            throw new UsernameNotFoundException(username);
        }

        return new User(usernameEnv, passwordEnv, emptyList());
    }
}
