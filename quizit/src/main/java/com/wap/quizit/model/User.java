package com.wap.quizit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "u_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_name", nullable = false, unique = true)
  private String username;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @ManyToOne
  @JoinColumn(name = "roles_r_id", referencedColumnName = "r_id", nullable = false)
  private Role role;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Quiz> quizzes;

  @OneToMany(mappedBy = "reportingUser", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Report> reportsIssued;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Comment> comments;
}
