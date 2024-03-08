package fr.pelliculum.restapi.user;

import lombok.Getter;

@Getter
public class UserDTO {
    // Getters
    private String lastname;
    private String firstname;
    private String username;
    private Integer followsCount;
    private Integer followersCount;
    // Autres champs souhaités

    public UserDTO() {
        // Constructeur vide
    }

    public UserDTO(String lastname, String firstname, String username) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        // Initialisation d'autres champs
    }

}
