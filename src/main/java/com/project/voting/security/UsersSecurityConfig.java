//package com.project.voting.security;
//
//import com.project.voting.domain.users.UsersRepository;
//import com.project.voting.service.users.UsersService;
//import com.project.voting.sms.service.SmsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@EnableWebSecurity
//@RequiredArgsConstructor
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class UsersSecurityConfig extends WebSecurityConfigurerAdapter {
//
//  private final SmsService smsService;
//  private final UsersService usersService;
//  private final UsersRepository usersRepository;
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http
//      .authorizeRequests()
//      .antMatchers("/verify-code")
//      .authenticated()
//      .and()
//      .formLogin()
//      .loginPage("/users/login")
//      .and()
//      .httpBasic().disable()
//      .csrf().disable()
//      .authorizeRequests()
////      .requestMatchers()  // 아래 명시한 path 는 CustomerSecurityConfig에서 담당
//      .antMatchers("/users/**")// 필요 시 상황에 맞게 /**, /* 등 추가
//      .hasAuthority("USER");
////      .permitAll()
////      .anyRequest().authenticated()
//    ;
//  }
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//  }
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(new UsersUserDetailsServiceImpl(usersRepository)).passwordEncoder(passwordEncoder());
//  }
//
//}
