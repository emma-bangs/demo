package com.hospital.service;

import com.hospital.model.Doctor;
import com.hospital.model.MedicalRecord;
import com.hospital.model.Patient;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository repo;
    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;

    public MedicalRecordService(MedicalRecordRepository repo, DoctorRepository doctorRepo, PatientRepository patientRepo) {
        this.repo = repo;
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
    }

    public MedicalRecord save(MedicalRecord record) {
        // Fetch doctor from DB
        Doctor doctor = doctorRepo.findById(record.getDoctor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        // Fetch patient from DB
        Patient patient = patientRepo.findById(record.getPatient().getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        record.setDoctor(doctor);
        record.setPatient(patient);

        return repo.save(record);
    }



    public List<MedicalRecord> getAll() {
        return repo.findAll();
    }

    public Optional<MedicalRecord> getById(Long id) {
        return repo.findById(id);
    }

    public List<MedicalRecord> getByPatientId(Long patientId) {
        return repo.findByPatientId(patientId);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
