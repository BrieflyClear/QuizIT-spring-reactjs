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
@EqualsAndHashCode(exclude = {"questions", "reportsIssued", "attempts", "categories"})
@Entity
@Table(name = "quizzes")
public class Quiz {

  @Id
  @Column(name = "q_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "is_public", nullable = false)
  private boolean isPublic;

  @Column(name = "title", nullable = false, length = 50)
  private String title;

  @ManyToOne
  @JoinColumn(name = "users_u_id", referencedColumnName = "u_id", nullable = false)
  private User author;

  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Question> questions;

  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<QuizCategory> categories;

  @OneToMany(mappedBy = "reportedQuiz", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Report> reportsIssued;

  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserQuizAttempt> attempts;
}
