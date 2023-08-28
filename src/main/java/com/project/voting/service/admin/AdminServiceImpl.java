package com.project.voting.service.admin;

import com.project.voting.domain.admin.Admin;
import com.project.voting.domain.admin.AdminRepository;

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
        optionalAdmin.orElseThrow(() -> new UsernameNotFoundException("관리자 ID가 존재하지 않습니다."));

        Admin admin = optionalAdmin.get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (admin.isAdminYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        return admin;
    }

    @Override
    public Optional<Admin> detail(Admin admin) {
        Optional<Admin> optionalAdmin = adminRepository.findById(admin.getAdminId());
        optionalAdmin.orElseThrow(() -> new RuntimeException("관리자 정보가 존재하지 않습니다."));
        return optionalAdmin;
    }


}
