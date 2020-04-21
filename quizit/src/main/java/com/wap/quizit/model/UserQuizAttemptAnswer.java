package com.wap.quizit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_quizzes_attempts_answers")
public class UserQuizAttemptAnswer {

  @Id
  @Column(name = "sqa_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "users_quizzes_attempts_sqa_id", referencedColumnName = "uqa_id", nullable = false)
  private UserQuizAttempt attempt;

  @ManyToOne
  @JoinColumn(name = "questions_question_id", referencedColumnName = "question_id", nullable = false)
  private Question question;

  @ManyToOne
  @JoinColumn(name = "answers_a_id", referencedColumnName = "a_id")
  private Answer answerGiven;
}
