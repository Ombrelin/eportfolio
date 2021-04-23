package com.arsene.eportfolio.model.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateSubjectDto {

    @NotBlank
    private Integer id;

    @NotBlank
    private String name;

    public UpdateSubjectDto() {
    }

    public UpdateSubjectDto(@NotNull @NotBlank Integer id, @NotNull @NotBlank String name) {
        this.id = id;
        this.name = name;
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
}
