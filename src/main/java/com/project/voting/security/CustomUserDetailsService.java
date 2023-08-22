//package com.project.voting.security;
//
//import com.project.voting.domain.admin.Admin;
//import com.project.voting.domain.admin.AdminRepository;
//import com.project.voting.domain.users.Users;
//import com.project.voting.domain.users.UsersRepository;
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
//public class CustomUsersDetailsService implements UserDetailsService {
//
//  private final UsersRepository usersRepository;
//
//
//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    Optional<Users> optionalUsers = usersRepository.findById(username);
//    if (!optionalUsers.isPresent()) {
//      throw new UsernameNotFoundException("사용자 정보가 존재하지 않습니다.");
//    }
//    Users users = optionalUsers.get();
//    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//    grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
//
//    return (UserDetails) users;
//  }
//}
