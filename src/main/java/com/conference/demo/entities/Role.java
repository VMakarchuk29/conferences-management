package com.conference.demo.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    MODERATOR, SPEAKER, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
