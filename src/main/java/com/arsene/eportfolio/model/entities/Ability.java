package com.arsene.eportfolio.model.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String color;

    private String image;

    @OneToMany(targetEntity=Technology.class, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Technology> technologies;

    public Ability(){ super(); }

    public Ability(Integer id, String name, String color, String image){
        super();
        this.setId(id);
        this.setName(name);
        this.setColor(color);
        this.setImage(image);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ability ability = (Ability) o;
        return getId().equals(ability.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Ability{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", image='" + image + '\'' +
                ", technologies=" + technologies +
                '}';
    }
}