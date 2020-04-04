package com.arsene.eportfolio.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String icon;

    private String image;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Ability.class, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Ability> abilities;
}