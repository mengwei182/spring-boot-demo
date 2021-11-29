package org.example.common.model;

import lombok.Data;
import org.example.entity.User;
import org.example.entity.UserGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
public class CustomUserDetails implements UserDetails {
    private User user;
    private Set<UserGrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<UserGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnable();
    }
}