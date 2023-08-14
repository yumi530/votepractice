package com.project.voting.domain.admin;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Admin implements UserDetails {
    @Id
    private String adminId;
    private String password;
    private boolean adminYn;

    @Builder
    public Admin(String adminId, String password, boolean adminYn){
        this.adminId = adminId;
        this.password = password;
        this.adminYn = adminYn;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return isAdminYn()
                ? List.of(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN"))
                : List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return adminId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
