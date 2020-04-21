package com.wap.quizit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quizzes_categories")
public class QuizCategory {

  @Id
  @Column(name = "qc_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "quizzes_q_id", referencedColumnName = "q_id", nullable = false)
  private Quiz quiz;

  @ManyToOne
  @JoinColumn(name = "categories_cat_id", referencedColumnName = "cat_id", nullable = false)
  private Category category;
}
