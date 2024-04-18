package com.microservice.user.dtos.user.response;

import com.microservice.user.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String surname;
    private String name;
    private String roleDescription;
    private Boolean status;
    private Long teamId;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.surname = user.getSurname();
        this.name = user.getName();
        this.roleDescription = user.getRole().getType();
        this.status = user.getStatus();
        this.teamId = getTeamId();
    }

}
