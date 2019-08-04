package com.arsene.eportfolio.model.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String icon;
    private String description;
    private String git;
    private String color;


    @ManyToMany
    private List<Technology> technologies;

    @ManyToMany
    private List<Ability> abilities;

    @ManyToOne
    private Subject subject;

    public Project() {
    }

    ;

    public Project(Integer id, String name, String icon, String description, String git, Subject subject, String color) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.git = git;
        this.subject = subject;
        this.technologies = new ArrayList<Technology>();
        this.abilities = new ArrayList<Ability>();
        this.color = color;
    }

    public Project(Integer id, String name, String icon, String description, String git, Subject subject, String color, List<Technology> technologies, List<Ability> abilities) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.git = git;
        this.subject = subject;
        this.technologies = technologies;
        this.abilities = abilities;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the git
     */
    public String getGit() {
        return git;
    }

    /**
     * @param git the git to set
     */
    public void setGit(String git) {
        this.git = git;
    }

    /**
     * @return the subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
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
     * @return the technologies
     */
    public List<Technology> getTechnologies() {
        return technologies;
    }

    /**
     * @param technologies the technologies to set
     */
    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
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


}