package fr.pelliculum.restapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
    public SignUpDto() {
    }

}