package org.example.service.dto;

import java.util.List;
import java.util.Objects;

import org.example.model.Patient;
public record PatientPageDto(
    List<PatientDto> patients,
    int page,
    int size,
    long total
){
  public PatientPageDto {
    Objects.requireNonNull(patients, "patients required");
    Objects.requireNonNull(page, "page required");
    Objects.requireNonNull(size, "size required");
    Objects.requireNonNull(total, "total required");
  }

  public static Builder newBuilder(){ return new Builder(); }
  public static Builder newBuilder(PatientPageDto dto) {
    return new Builder(dto);
  }

  public Builder toBuilder() {
    return new Builder(this) {
    };
  }

  public static class Builder {
    private List<PatientDto> patients;
    int page;
    int size;
    long total;
    public Builder() {
    }

    public Builder(PatientPageDto dto){
      this.patients = dto.patients;
      this.page = dto.page;
      this.size = dto.size();
      this.total = dto.total;
    }

    public Builder patients(List<PatientDto> patients) {
      this.patients = patients;
      return this;
    }

    public Builder page(int page) {
      this.page = page;
      return this;
    }

    public Builder size(int size) {
      this.size = size;
      return this;
    }

    public Builder total(long total) {
      this.total = total;
      return this;
    }

    public PatientPageDto build() {
      return new PatientPageDto(
          patients,
          page,
          size,
          total
      );
    }

  }
}
