package com.project.voting.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import org.springframework.data.annotation.CreatedDate;

public class BaseEntity {

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdDate;

}
