package com.wap.quizit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"quizzes", "reportsIssued", "comments", "quizAttemps"})
@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "u_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false, unique = true, length = 15)
  private String username;

  @Column(name = "email", nullable = false, unique = true, length = 50)
  private String email;

  @Column(name = "password", nullable = false, length = 60)
  private String password;

  @Column(name = "is_active_premium", nullable = false)
  private boolean isActivePremium;

  @ManyToOne
  @JoinColumn(name = "roles_r_id", referencedColumnName = "r_id", nullable = false)
  private Role role;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Quiz> quizzes;

  @OneToMany(mappedBy = "reportingUser", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Report> reportsIssued;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Comment> comments;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserQuizAttempt> quizAttempts;
}
