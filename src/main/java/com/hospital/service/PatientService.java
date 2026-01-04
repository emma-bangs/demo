package com.hospital.service;

import com.hospital.model.Patient;
import com.hospital.repository.PatientRepository;
import com.hospital.exceptions.DuplicateFieldException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public List<Patient> getAll() {
        return repo.findAll();
    }

    public Optional<Patient> getById(Long id) {
        return repo.findById(id);
    }

    public Patient save(Patient patient) {
        Optional<Patient> existing = repo.findByPhone(patient.getPhone());

        if (patient.getId() == null) {
            // Creating new patient
            if (existing.isPresent()) {
                throw new DuplicateFieldException("Phone number already in use by another patient");
            }
        } else {
            // Updating existing patient
            if (existing.isPresent() && !existing.get().getId().equals(patient.getId())) {
                throw new DuplicateFieldException("Phone number already in use by another patient");
            }
        }

        return repo.save(patient);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
