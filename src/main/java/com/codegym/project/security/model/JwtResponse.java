package com.codegym.project.security.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String name;
    private String email;
    private String phone;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String accessToken, Long id, String username, String name, Collection<? extends GrantedAuthority> roles, String email, String phone, String avatar) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.id = id;
    }
}