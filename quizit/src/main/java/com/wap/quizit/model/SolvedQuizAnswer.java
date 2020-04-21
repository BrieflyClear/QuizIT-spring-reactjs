package com.wap.quizit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "solved_quiz_answers")
public class SolvedQuizAnswer {

  @Id
  @Column(name = "sqa_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "users_u_id", referencedColumnName = "u_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "questions_question_id", referencedColumnName = "question_id", nullable = false)
  private Question question;

  @ManyToOne
  @JoinColumn(name = "answers_a_id", referencedColumnName = "a_id")
  private Answer answerGiven;
}
