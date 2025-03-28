package com.example.demo.entity;

import com.example.demo.constant.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
@SQLRestriction("deleted_at IS NULL")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "UUID")
  private UUID id;

  @Column(name = "full_name", nullable = false, length = 100, unique = true)
  private String username;

  @Column(nullable = false, length = 100, unique = true)
  private String email;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name= "created_at")
  private Date createdAt;

  @Column(name = "deleted_at")
  private Date deletedAt;

  public Role getRole() {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

}
