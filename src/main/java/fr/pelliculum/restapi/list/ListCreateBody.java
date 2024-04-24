package fr.pelliculum.restapi.list;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListCreateBody {

    private String name;
    private String description;
    private Boolean isPublic;
    private String username;

}
