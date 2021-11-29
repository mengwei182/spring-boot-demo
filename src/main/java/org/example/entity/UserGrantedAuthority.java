package org.example.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class UserGrantedAuthority implements GrantedAuthority {
    private String authority;

    public UserGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}