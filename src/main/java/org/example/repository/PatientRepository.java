package org.example.repository;

import org.example.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, String> {
  @Query(value = "SELECT * FROM Patient p WHERE p.id LIKE :word% " +
      "OR p.first_name LIKE :word% OR p.last_name LIKE :word% ",
      nativeQuery = true)
  Page<Patient> findByIdLikeOrFirstNameLikeOrLastNameLike(@Param("word") String word,
                        Pageable pageable);

}
