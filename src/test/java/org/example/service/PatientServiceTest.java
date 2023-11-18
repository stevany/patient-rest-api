package org.example.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.example.model.Address;
import org.example.model.Patient;
import org.example.repository.AddressRepository;
import org.example.repository.PatientRepository;
import org.example.service.dto.PatientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
  @Mock
  private PatientRepository patientRepository;
  @Mock
  private AddressRepository addressRepository;
  @InjectMocks
  private PatientService service;

  Patient getPatient(){
    Patient patient = new Patient();
    patient.setId(UUID.randomUUID().toString());
    patient.setFirstName("test");
    patient.setLastName("last name");
    patient.setDob("01-12-2023");
    patient.setPhoneNumber("123");
    return patient;
  }
  Address getAddress(Patient patient) {
    Address address = new Address();
    address.setPatient(patient);
    address.setId(UUID.randomUUID().toString());
    address.setSuburb("suburb");
    address.setPostcode("123");
    address.setState("state");
    return address;
  }

  @Test
  void givenFilterAndPageAndSize_whenSearchPatient_ThenPatientList() {
    Patient patient = getPatient();

    Address address = getAddress(patient);
    List<Patient> patients = new ArrayList<>();
    patients.add(patient);
    Page<Patient> pagedResponse = new PageImpl(patients);
    when(patientRepository.findByIdLikeOrFirstNameLikeOrLastNameLike(any(String.class), any(Pageable.class)))
        .thenReturn(pagedResponse);
    when(addressRepository.findByPatientId(any(String.class)))
        .thenReturn(address);
    var result = service.findAll("test", 1, 5);
    assertThat(result.page()).isEqualTo(1);
    assertThat(result.patients().size()).isEqualTo(1);

    assertThat(result.patients().get(0)).isInstanceOf(PatientDto.class);

  }

  @Test
  void givenFilterAndPageAndSize_whenSearchPatientAndAddressNotFound_ThenThrowError() {
    Patient patient = getPatient();

    List<Patient> patients = new ArrayList<>();
    patients.add(patient);
    Page<Patient> pagedResponse = new PageImpl(patients);
    when(patientRepository.findByIdLikeOrFirstNameLikeOrLastNameLike(any(String.class), any(Pageable.class)))
        .thenReturn(pagedResponse);
    when(addressRepository.findByPatientId(any(String.class)))
        .thenReturn(null);
    assertThatThrownBy(() -> service.findAll("test", 1, 5))
        .isNotNull()
        .hasMessageContaining("Address not found");

  }
  @Test
  void givenFilterAndPageAndSize_whenSearchPatientAndPatientNotFound_ThenThrowError() {
    when(patientRepository.findById(any(String.class)))
        .thenReturn(Optional.ofNullable(null));
    assertThatThrownBy(() -> service.findById("123"))
        .isNotNull()
        .hasMessageContaining("Patient not found");

  }
  @Test
  void givenPatientData_whenAddPatient_ThenReturnPatientData() {
    Patient patient = getPatient();

    Address address = getAddress(patient);
    when(patientRepository.save(any(Patient.class)))
        .thenReturn(patient);
    when(addressRepository.save(any(Address.class)))
        .thenReturn(address);
    var result = service.add(service.mapToPatientDto(patient, address));
    assertThat(result).isInstanceOf(PatientDto.class);
    assertThat(result).hasFieldOrProperty("id");
    assertThat(result).hasFieldOrProperty("firstName");
    assertThat(result).hasFieldOrProperty("lastName");
  }

  @Test
  void givenPatientData_whenUpdatePatient_ThenReturnPatientData() {
    Patient patient = getPatient();

    Address address = getAddress(patient);
    when(patientRepository.findById(any(String.class)))
        .thenReturn(Optional.ofNullable(patient));
    when(addressRepository.findByPatientId(any(String.class)))
        .thenReturn(address);
    when(patientRepository.save(any(Patient.class)))
        .thenReturn(patient);
    when(addressRepository.save(any(Address.class)))
        .thenReturn(address);
    var result = service.update("123", service.mapToPatientDto(patient, address));
    assertThat(result).isInstanceOf(PatientDto.class);
    assertThat(result).hasFieldOrProperty("id");
    assertThat(result).hasFieldOrProperty("firstName");
    assertThat(result).hasFieldOrProperty("lastName");
  }


}
