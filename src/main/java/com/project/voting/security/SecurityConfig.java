//package com.project.voting.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//  private final CustomAdminDetailsService adminDetailsService;
//  private final CustomUsersDetailsService usersDetailsService;
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.authenticationProvider(userAuthenticationProvider())
//      .authenticationProvider(adminAuthenticationProvider());
//  }
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http
//      .authorizeRequests()
//      .antMatchers("/users/**").hasRole("USER")
//      .antMatchers("/admin/**").hasRole("ADMIN")
//      .anyRequest().authenticated()
//      .and()
//      .formLogin()
//      .loginPage("/login")
//      .permitAll()
//      .and()
//      .logout()
//      .permitAll();
//  }
//
//  @Bean
//  public AuthenticationProvider userAuthenticationProvider() {
//    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//    authenticationProvider.setUserDetailsService(usersDetailsService);
//    authenticationProvider.setPasswordEncoder(passwordEncoder());
//    authenticationProvider.setAuthoritiesMapper(new SimpleAuthorityMapper());
//    return authenticationProvider;
//  }
//
//  @Bean
//  public AuthenticationProvider adminAuthenticationProvider() {
//    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//    authenticationProvider.setUserDetailsService(adminDetailsService);
//    authenticationProvider.setPasswordEncoder(passwordEncoder());
//    authenticationProvider.setAuthoritiesMapper(new SimpleAuthorityMapper());
//    return authenticationProvider;
//  }
//
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//}
//
