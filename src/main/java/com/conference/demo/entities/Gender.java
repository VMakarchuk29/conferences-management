package com.conference.demo.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Gender implements GrantedAuthority {
    MALE, FEMALE;

    @Override
    public String getAuthority() {
        return name();
    }
}
