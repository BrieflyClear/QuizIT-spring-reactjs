package com.wap.quizit.service;

import com.wap.quizit.model.Comment;
import com.wap.quizit.repository.CommentRepository;
import com.wap.quizit.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
  
  private CommentRepository commentRepository;

  public Comment getById(Long id) {
    return commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Comment.class, id));
  }

  public Optional<Comment> getByIdNoException(Long id) {
    return commentRepository.findById(id);
  }

  public List<Comment> getAll() {
    return commentRepository.findAll();
  }

  public Comment save(Comment comment) {
    return commentRepository.save(comment);
  }

  public void deleteById(Long id) {
    commentRepository.deleteById(id);
  }
}
