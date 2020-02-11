package com.arsene.eportfolio.model.entities;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.List;

import javax.persistence.*;

@Entity
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String icon;

    private String image;

    @OneToMany(targetEntity = Ability.class, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Ability> abilities;
}