package com.microservice.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.server.SecurityWebFilterChain;


/*

@EnableMethodSecurity
*/
@Configuration
public class SecurityConfig {

    /*
    @Autowired
    private JwtUtils jwtUtils;
    */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> {
                    auth.anyExchange().authenticated();
                })
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public ReactiveClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryReactiveClientRegistrationRepository(this.clientRegistration());
    }


    private ClientRegistration clientRegistration() {
        return ClientRegistration.withRegistrationId("default")
                .clientId("microservices_client")
                .clientSecret("dAuywT9ejF1DbXWuUudKRXssQlhmbqKj")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/keycloak")
                .scope("openid")
                .authorizationUri("http://localhost:8181/realms/microservices-realm/protocol/openid-connect/auth")
                .tokenUri("http://localhost:8181/realms/microservices-realm/protocol/openid-connect/token")
                .userInfoUri("http://localhost:8181/realms/microservices-realm/protocol/openid-connect/userinfo")
                .jwkSetUri("http://localhost:8181/realms/microservices-realm/protocol/openid-connect/certs")
                .clientName("Microservices Client")
                .build();
    }

}

    /*
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailService userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    // EndPoints publics
                    http.requestMatchers(HttpMethod.POST, "/api/users/log-in").permitAll();

                    // EndPoints user privates
                    http.requestMatchers(HttpMethod.GET, "/api/users").hasRole("DVELOPER");
                    http.requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN","DVELOPER");
                    http.requestMatchers(HttpMethod.POST, "/api/users/**").hasAnyRole("ADMIN","DVELOPER");
                    http.requestMatchers(HttpMethod.POST, "/api/users").hasAnyRole("ADMIN","DVELOPER");
                    http.requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole("ADMIN","DEVELOPER");
                    http.requestMatchers(HttpMethod.PUT, "/api/method/**").hasAnyRole("ADMIN","DEVELOPER");
                    http.requestMatchers(HttpMethod.PATCH, "/api/users/**").hasAnyRole("ADMIN","DEVELOPER");
                    http.requestMatchers(HttpMethod.PATCH, "/api/users/subscription/**").hasRole("DVELOPER");

                    //Endpoints player privates
                    http.requestMatchers(HttpMethod.GET, "/api/players/**").hasAnyRole("ADMIN","USER","DEVELOPER");
                    http.requestMatchers(HttpMethod.POST, "/api/players").hasAnyRole("ADMIN","USER","DEVELOPER");
                    http.requestMatchers(HttpMethod.POST, "/api/players/scouter").hasAnyRole("ADMIN","DEVELOPER");
                    http.requestMatchers(HttpMethod.DELETE, "/api/players/**").hasAnyRole("ADMIN","DEVELOPER");
                    http.requestMatchers(HttpMethod.PUT, "/api/players/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/players/scouter").hasAnyRole("ADMIN","DEVELOPER");
                    http.requestMatchers(HttpMethod.PUT, "/api/players/scouter").hasAnyRole("ADMIN","DEVELOPER");
                    http.requestMatchers(HttpMethod.PATCH, "/api/players/**").hasRole("ADMIN");

                    //Endpoints team privates
                    http.requestMatchers(HttpMethod.GET, "/api/teams").hasRole("DVELOPER");
                    http.requestMatchers(HttpMethod.GET, "/api/teams/**").hasRole("DVELOPER");
                    http.requestMatchers(HttpMethod.POST, "/api/teams").hasRole("DVELOPER");
                    http.requestMatchers(HttpMethod.PUT, "/api/teams/**").hasRole("DVELOPER");
                    http.requestMatchers(HttpMethod.DELETE, "/api/teams/**").hasRole("DVELOPER");
                    http.requestMatchers(HttpMethod.PATCH, "/api/teams/**").hasRole("DVELOPER");

                    //Endpoints calendar privates
                    http.requestMatchers(HttpMethod.GET, "/api/calendar/events").hasAnyRole("DVELOPER","ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/calendar/events/**").hasAnyRole("DVELOPER","ADMIN");
                    http.requestMatchers(HttpMethod.POST, "/api/calendar/**").hasAnyRole("DVELOPER","ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/calendar/**").hasAnyRole("DVELOPER","ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/calendar/**").hasAnyRole("DVELOPER","ADMIN");

                    http.anyRequest().denyAll();

     */

