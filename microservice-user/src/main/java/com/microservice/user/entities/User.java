package com.microservice.user.entities;

import com.microservice.user.dtos.user.request.UserRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    //@ManyToOne
    //private Role role;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "account_no_expired")
    private boolean accountNoExpired;
    @Column(name = "account_no_locked")
    private boolean accountNoLocked;
    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired;


    @Column(nullable = false)
    private Boolean subscriptionStatus;

    private Long teamId;

    public User(UserRequestDTO requestDTO, Role role) {
        this.username = requestDTO.getUsername();
        this.surname = requestDTO.getSurname();
        this.name = requestDTO.getName();
        this.password = requestDTO.getPassword();
        //falta role y los status...
        this.subscriptionStatus = true;
        this.teamId = requestDTO.getTeamId();
    }
}
