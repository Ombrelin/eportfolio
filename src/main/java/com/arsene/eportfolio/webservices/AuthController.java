package com.arsene.eportfolio.webservices;


import com.arsene.eportfolio.dtos.AuthDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @PostMapping("/auth")
    public AuthDTO auth() {
        return new AuthDTO(true);
    }
}
