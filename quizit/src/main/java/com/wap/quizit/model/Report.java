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
@Table(name = "reports")
public class Report {

  @Id
  @Column(name = "r_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false, length = 50)
  private String title;

  @Column(name = "description", nullable = false, length = 200)
  private String description;

  @Column(name = "status", nullable = false, length = 20)
  private String status;

  @Column(name = "issued_time", nullable = false)
  private LocalDateTime issuedTime;

  @ManyToOne
  @JoinColumn(name = "quizzes_q_id", referencedColumnName = "q_id", nullable = false)
  private Quiz reportedQuiz;

  @ManyToOne
  @JoinColumn(name = "users_u_id", referencedColumnName = "u_id", nullable = false)
  private User reportingUser;
}
