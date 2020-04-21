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
@Table(name = "answers")
public class Answer {

  public static final Integer MAX_POINT_COUNT = 50;
  public static final Integer MIN_POINT_COUNT = -50;

  @Id
  @Column(name = "a_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "answer_contents", nullable = false, length = 4000)
  private String contents;

  @Column(name = "is_correct", nullable = false)
  private boolean isCorrect;

  @Column(name = "points_count", nullable = false)
  private Integer pointsCount;

  @ManyToOne
  @JoinColumn(name = "questions_question_id", referencedColumnName = "question_id", nullable = false)
  private Question question;

  @OneToMany(mappedBy = "answerGiven", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<SolvedQuizAnswer> solvedQuizAnswers;
}
