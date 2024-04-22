package com.microservice.gateway.config;

import com.microservice.gateway.config.filter.JwtTokenValidator;
import com.microservice.gateway.services.UserDetailService;
import com.microservice.gateway.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
*/
public class SecurityConfig {

    /*
    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
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
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

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

     */
}
