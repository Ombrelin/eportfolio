package com.arsene.eportfolio.dtos;

public class AuthDTO {

    private Boolean success;

    public AuthDTO() {
    }

    public AuthDTO(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
