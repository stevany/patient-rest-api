package org.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="address")
public class Address {
  @Id
  private String id;
  @Column(name="address")
  private String address;
  @Column(name="suburb")
  private String suburb;
  @Column(name="state")
  private String state;
  @Column(name="postcode")
  private String postcode;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="patient_id")
  private Patient patient;

}
