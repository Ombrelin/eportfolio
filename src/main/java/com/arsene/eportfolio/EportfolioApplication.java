package com.arsene.eportfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class EportfolioApplication {

    public static void main(String[] args) {
        SpringApplication.run(EportfolioApplication.class, args);
    }

    @GetMapping("/")
    public String welcome(){
        return "index.html";
    }
}
