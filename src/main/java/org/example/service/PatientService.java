package org.example.service;

import java.util.Optional;
import java.util.UUID;
import org.example.model.Address;
import org.example.model.Patient;
import org.example.repository.AddressRepository;
import org.example.repository.PatientRepository;
import org.example.service.dto.PatientDto;
import org.example.service.dto.PatientPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
  @Autowired
  private PatientRepository patientRepository;
  @Autowired
  private AddressRepository addressRepository;

  public PatientPageDto findAll(String word, int page, int size) {
    Pageable paging = PageRequest.of(page - 1, size);
    Page<Patient> patients = patientRepository.findByIdLikeOrFirstNameLikeOrLastNameLike(word, paging);
    return PatientPageDto.newBuilder()
        .patients(patients.getContent().stream().map(patient -> {
              var address = findAddressByPatientId(patient.getId());
              return mapToPatientDto(patient, address);
            }
        ).toList())
        .page(page)
        .size(size)
        .total(patients.getTotalElements())
        .build();

  }

  public PatientDto add(PatientDto patientDto) {
    Patient patient = mapToPatient(patientDto);
    Address address = mapToAddress(patient, patientDto);
    patientRepository.save(patient);
    addressRepository.save(address);
    patientDto.setId(patient.getId());
    return patientDto;

  }
  public Optional<Patient> findById(String id) {
    var existPatient = patientRepository.findById(id);

    if(existPatient.isEmpty()) {
      throw new RuntimeException("Patient not found %s".formatted(id));
    }
    return existPatient;
  }



  public Address findAddressByPatientId(String id) {
    var existAddress = addressRepository.findByPatientId(id);
    if(existAddress == null) {
      throw new RuntimeException("Address not found %s".formatted(id));
    }
    return existAddress;
  }

  public PatientDto update(String id, PatientDto patientDto) {
    findById(id);
    var existAddress = findAddressByPatientId(id);
    Patient patient = mapToPatient(patientDto);
    patient.setId(id);
    Address address = mapToAddress(patient, patientDto);
    address.setId(existAddress.getId());
    addressRepository.save(address);
    patientRepository.save(patient);
    patientDto.setId(patient.getId());

    return patientDto;

  }

  public String remove(String id) {
    findById(id);
    var existAddress = findAddressByPatientId(id);

    addressRepository.deleteById(existAddress.getId());
    patientRepository.deleteById(id);
    return id;
  }
  public PatientDto mapToPatientDto(Patient patient, Address address) {
    PatientDto patientDto = new PatientDto();
    patientDto.setId(patient.getId());
    patientDto.setFirstName(patient.getFirstName());
    patientDto.setLastName(patient.getLastName());
    patientDto.setGender(patient.getGender());
    patientDto.setDob(patient.getDob());
    patientDto.setAddress(address.getAddress());
    patientDto.setSuburb(address.getSuburb());
    patientDto.setState(address.getState());
    patientDto.setPostcode(address.getPostcode());
    patientDto.setPhoneNumber(patient.getPhoneNumber());
    return patientDto;
  }

  public Patient mapToPatient(PatientDto patientDto) {
    Patient patient = new Patient();
    patient.setId(UUID.randomUUID().toString());
    patient.setFirstName(patientDto.getFirstName());
    patient.setLastName(patientDto.getLastName());
    patient.setGender(patientDto.getGender());
    patient.setDob(patientDto.getDob());
    patient.setPhoneNumber(patientDto.getPhoneNumber());
    return patient;
  }

  public Address mapToAddress(Patient patient, PatientDto patientDto) {
    Address address = new Address();
    address.setId(UUID.randomUUID().toString());
    address.setPatient(patient);
    address.setAddress(patientDto.getAddress());
    address.setSuburb(patientDto.getSuburb());
    address.setState(patientDto.getState());
    address.setPostcode(patientDto.getPostcode());
    return address;
  }
}
