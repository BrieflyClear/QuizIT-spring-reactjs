package com.wap.quizit.web.rest;

import com.wap.quizit.service.dto.ApplicationInfoDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/application")
@AllArgsConstructor
public class ApplicationRestController {

  @GetMapping("/info")
  public ResponseEntity<ApplicationInfoDTO> get() {
    return new ResponseEntity<>(ApplicationInfoDTO.builder().build(), HttpStatus.OK);
  }
}
