package org.example.controller;

import org.example.model.Patient;
import org.example.service.PatientService;
import org.example.service.dto.PatientDto;
import org.example.service.dto.PatientPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
@RequestMapping("/api/patients")
public class PatientController {
  @Autowired
  private PatientService service;

  @GetMapping
  public PatientPageDto findAll(
      @RequestParam(defaultValue = "") String filter,
      @RequestParam int page, @RequestParam int size) {
    return service.findAll(filter, page, size);
  }

  @PostMapping
  public PatientDto add(@RequestBody PatientDto body) {
    return service.add(body);
  }

  @PutMapping
  public PatientDto update(@RequestParam String id, @RequestBody PatientDto body) {
    return service.update(id, body);
  }
  @DeleteMapping
  public String  update(@RequestParam String id) {
    return service.remove(id);
  }
}
