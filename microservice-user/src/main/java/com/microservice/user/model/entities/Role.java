package com.microservice.user.model.entities;

import com.microservice.user.model.dtos.role.request.RoleRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "role_name")
    private String roleName;

    /*
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();
     */

    public Role(RoleRequestDTO requestDTO) {
        this.roleName = requestDTO.getRoleName();
        //this.permissions.addAll(requestDTO.getPermissions());
    }

}
