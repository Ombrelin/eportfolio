package com.arsene.eportfolio.model.entities;

import org.hibernate.annotations.Cascade;

import java.util.List;

import javax.persistence.*;

@Entity
public class Subject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String icon;

    private String image;

    @OneToMany( targetEntity=Ability.class, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Ability> abilities;

    public Subject(){ super(); }

    public Subject(Integer id, String name, String icon, String image, List<Ability> abilities){
        super();
        this.id = id;
        this.name = name;
        this.abilities = abilities;
        this.image = image;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the abilities
     */
    public List<Ability> getAbilities() {
        return abilities;
    }

    /**
     * @param abilities the abilities to set
     */
    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}