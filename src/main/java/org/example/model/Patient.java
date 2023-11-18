package org.example.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name="patient")
public class Patient {
  @Id
  private String id;

  @Column(name="first_name")
  private String firstName;
  @Column(name="last_name")
  private String lastName;
  @Enumerated(EnumType.STRING)
  @Column(name="gender")
  private Gender gender;
  @Column(name="dob")
  private String dob;
  @OneToMany(mappedBy = "address")
  private List<Address> addresses;
  @Column(name="phone_number")
  private String phoneNumber;
  @CreationTimestamp
  @Column(name="created_at", nullable = false, updatable = false, insertable = true)
  private LocalDateTime createdAt;
  @UpdateTimestamp
  @Column(name="updated_at", nullable = false, updatable = true, insertable = true)
  private LocalDateTime updatedAt;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Patient patient = (Patient) o;
    return Objects.equals(id, patient.id) && Objects.equals(firstName, patient.firstName) &&
        Objects.equals(lastName, patient.lastName) && gender == patient.gender && Objects.equals(dob, patient.dob) &&
        Objects.equals(addresses, patient.addresses) && Objects.equals(phoneNumber, patient.phoneNumber) &&
        Objects.equals(createdAt, patient.createdAt) && Objects.equals(updatedAt, patient.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, gender, dob, addresses, phoneNumber, createdAt, updatedAt);
  }
}