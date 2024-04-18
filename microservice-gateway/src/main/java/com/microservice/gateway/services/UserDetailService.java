package com.microservice.gateway.services;

import com.microservice.gateway.client.UserClient;
import com.microservice.gateway.dtos.AuthLoginRequestDTO;
import com.microservice.gateway.dtos.UserDTO;
import com.microservice.gateway.http.AuthResponse;
import com.microservice.gateway.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }

    /*
    public AuthResponse createUser(AuthCreateUserRequest createRoleRequest) {

        String username = createRoleRequest.username();
        String password = createRoleRequest.password();
        List<String> rolesRequest = createRoleRequest.roleRequest().roleListName();

        Set<RoleEntity> roleEntityList = roleRepository.findRoleEntitiesByRoleEnumIn(rolesRequest).stream().collect(Collectors.toSet());

        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist.");
        }

        UserEntity userEntity = UserEntity.builder().username(username).password(passwordEncoder.encode(password)).roles(roleEntityList).isEnabled(true).accountNoLocked(true).accountNoExpired(true).credentialNoExpired(true).build();

        UserEntity userSaved = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userSaved.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userSaved.getRoles().stream().flatMap(role -> role.getPermissionList().stream()).forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        String accessToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(username, "User created successfully", accessToken, true);
        return authResponse;
    }
    */

    public AuthResponse loginUser(AuthLoginRequestDTO authLoginRequest) {

        //obtengo UserDetails por credenciales
        UserDetails userDetails = this.getUserDetailsByCredentials(authLoginRequest);
        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Invalid username or password"));
        }

        //verify match between passwords (request, entity persisted)
        if (!passwordEncoder.matches(authLoginRequest.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        //autentico el usuario por sus credenciales
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //creo un token
        String accessToken = jwtUtils.createToken(authentication);

        //creo el response
        AuthResponse authResponse = new AuthResponse()
                .builder()
                .username(authLoginRequest.getUsername())
                .message("User logged succesfully!")
                .jwt(accessToken)
                .status(true)
                .build();

        return authResponse;
    }

    //Autentico usuario por credenciales
    /*
    public Authentication authenticateUserLogin(AuthLoginRequestDTO authLoginRequestDTO) {

        //obtengo UserDetails por credenciales
        UserDetails userDetails = this.getUserDetailsByCredentials(authLoginRequestDTO);
        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Invalid username or password"));
        }

        //PASSWORD VALIDATION
        //if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        //    throw new BadCredentialsException("Incorrect Password");
        //}

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

     */

    public UserDetails getUserDetailsByCredentials(AuthLoginRequestDTO authLoginRequestDTO) {

        //solicito usuario por credenciales al user-microservice
        UserDTO user = userClient.startSession(authLoginRequestDTO);

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //cargo lista de autorizaciones (roles, permisos)
        user.getRoles().forEach(role -> authorityList.add(
                new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

        user.getRoles().stream().flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNoExpired(),
                user.isCredentialNoExpired(),
                user.isAccountNoLocked(),
                authorityList
        );
    }
}
