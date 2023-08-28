package com.project.voting.service.admin;

import com.project.voting.domain.admin.Admin;

import java.io.Serializable;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdminService extends UserDetailsService  {

  Optional<Admin> detail(Admin admin);

}
