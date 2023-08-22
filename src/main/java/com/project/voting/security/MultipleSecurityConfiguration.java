//package com.project.voting.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//public class MultipleSecurityConfiguration extends WebSecurityConfigurerAdapter {
//  // spring security에서 허용할 web 리소스 path
//  public static final String[] SECURITY_EXCLUDE_PATTERN_ARR = {
//    "/"
//
//    // resource
//    , "/error/**"
//    , "/favicon.ico"
//    , "/resources/**"
//
//    // User 관련
//    , "/users/login*"
//
//    // Admin 관련
//    , "/admin/login*"
//  };
//  @Autowired
//  private CustomAuthenticationProvider customAuthenticationProvider;
//
//  @Bean
//  public AuthenticationProvider authenticationProvider() {
//    return customAuthenticationProvider;
//  }
//  @Autowired
//  private AuthenticationProvider authenticationProvider;
//
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.authenticationProvider(authenticationProvider);
//  }
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//
//
//
//
//  }
