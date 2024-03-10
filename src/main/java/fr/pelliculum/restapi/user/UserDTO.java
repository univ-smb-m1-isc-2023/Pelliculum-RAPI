package fr.pelliculum.restapi.user;

import lombok.Getter;

@Getter
public class UserDTO {
    // Getters
    private String lastname;
    private String firstname;
    private String username;
    private Long followsCount;
    private Long followersCount;
    private Boolean isFollowed;
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

    public UserDTO(String lastname, String firstname, String username, Long followsCount, Long followersCount, Boolean isFollowed) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        this.followsCount = followsCount;
        this.followersCount = followersCount;
        this.isFollowed = isFollowed;
    }

}
