package org.example.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import org.example.model.Gender;
@Data
public class PatientDto {
  private String id;
  @NotBlank
  @Size(min = 1, max = 150, message = "First Name must be between 1 and 150 characters")
  private String firstName;
  @NotBlank
  @Size(min = 1, max = 150, message = "Last Name must be between 1 and 150 characters")
  private String lastName;
  @NotBlank
  private Gender gender;
  @NotBlank
  private String dob;
  @NotBlank
  private String address;
  @NotBlank
  private String suburb;
  @NotBlank
  private String state;
  @NotBlank
  private String postcode;
  @NotBlank
  private String phoneNumber;
}