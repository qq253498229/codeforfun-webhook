package com.example.codeforfunwebhook.hooks;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author wangbin
 */
@Entity
@Table
@Data
public class Hooks {
  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid")
  @GeneratedValue(generator = "uuid")
  private String id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;
}
