package com.wap.quizit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

  @Id
  @Column(name = "c_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "contents", nullable = false, length = 200)
  private String contents;

  @Column(name = "issued_time", nullable = false)
  private LocalDateTime issuedTime;

  @ManyToOne
  @JoinColumn(name = "users_u_id", referencedColumnName = "u_id", nullable = false)
  private User author;

  @ManyToOne
  @JoinColumn(name = "questions_question_id", referencedColumnName = "question_id", nullable = false)
  private Question question;
}
