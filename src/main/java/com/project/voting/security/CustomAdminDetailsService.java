//package com.project.voting.security;
//
//import com.project.voting.domain.admin.Admin;
//import com.project.voting.domain.admin.AdminRepository;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//@RequiredArgsConstructor
//public class CustomAdminDetailsService implements UserDetailsService {
//
//  private final AdminRepository adminRepository;
//
//
//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    Optional<Admin> optionalAdmin = adminRepository.findById(username);
//    if (!optionalAdmin.isPresent()) {
//      throw new UsernameNotFoundException("관리자 정보가 존재하지 않습니다.");
//    }
//    Admin admin = optionalAdmin.get();
//    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
////        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
//    if (admin.isAdminYn()) {
//      grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
//    }
//    return admin;
//  }
//}
//
