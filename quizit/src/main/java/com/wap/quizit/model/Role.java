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
@EqualsAndHashCode(exclude = {"users"})
@Entity
@Table(name = "roles")
public class Role {

  @Id
  @Column(name = "r_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, unique = true, length = 20)
  private String name;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<User> users;
}
