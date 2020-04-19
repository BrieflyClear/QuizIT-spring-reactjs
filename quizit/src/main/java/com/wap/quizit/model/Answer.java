package com.wap.quizit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {

  @Id
  @Column(name = "a_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "answer_contents", nullable = false)
  private String contents;

  @Column(name = "correct", nullable = false)
  private boolean isCorrect;

  @ManyToOne
  @JoinColumn(name = "questions_question_id", referencedColumnName = "question_id", nullable = false)
  private Question question;
}
