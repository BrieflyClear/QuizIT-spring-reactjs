package com.wap.quizit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_quizzes_attempts")
public class UserQuizAttempt {

  @Id
  @Column(name = "uqa_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "users_u_id", referencedColumnName = "u_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "quizzes_q_id", referencedColumnName = "u_id", nullable = false)
  private Quiz quiz;

  // TODO (other entities too) assign Time.NOW() value when creating new entity
  @Column(name = "attemt_time", nullable = false)
  private LocalDateTime attemptTime;

  @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserQuizAttemptAnswer> answers;
}
