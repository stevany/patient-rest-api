package org.example.repository;

import org.example.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, String> {
  @Query(value = "SELECT * FROM ADDRESS a WHERE a.patient_id = :patientId", nativeQuery = true)
  Address findByPatientId(@Param("patientId") String id);
}
