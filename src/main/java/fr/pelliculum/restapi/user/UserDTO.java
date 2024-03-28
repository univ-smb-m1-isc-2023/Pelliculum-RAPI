package fr.pelliculum.restapi.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String lastname;
    private String firstname;
    private String username;
    private Long followsCount;
    private Long followersCount;
    private Boolean isFollowed;

    public UserDTO(String lastname, String firstname, String username) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
    }

}
