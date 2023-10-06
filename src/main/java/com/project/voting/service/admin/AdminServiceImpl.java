package com.project.voting.service.admin;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.admin.AdminRepository;

import com.project.voting.exception.admin.AdminCustomException;
import com.project.voting.exception.admin.AdminErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService  {
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Admin> optionalAdmin = adminRepository.findById(username);
        optionalAdmin.orElseThrow(() -> new AdminCustomException(AdminErrorCode.ADMIN_NOT_FOUND));

        Admin admin = optionalAdmin.get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (admin.isAdminYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        return admin;
    }

}
