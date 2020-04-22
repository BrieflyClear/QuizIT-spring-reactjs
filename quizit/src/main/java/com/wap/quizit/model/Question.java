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
@EqualsAndHashCode(exclude = {"userQuizAttemptsAnswers", "comments", "answers"})
@Entity
@Table(name = "questions")
public class Question {

  @Id
  @Column(name = "question_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "question_contents", nullable = false, length = 1000)
  private String contents;

  @Column(name = "is_multiple_choice", nullable = false)
  private boolean isMultipleChoice;

  @Column(name = "is_closed", nullable = false)
  private boolean isClosed;

  @ManyToOne
  @JoinColumn(name = "quizzes_q_id", referencedColumnName = "q_id", nullable = false)
  private Quiz quiz;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Answer> answers;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Comment> comments;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserQuizAttemptAnswer> userQuizAttemptsAnswers;

  public boolean removeAnswer(Answer answer) {
    return answers.remove(answer);
  }
}
