package com.hospital.controller;

import com.hospital.dto.MedicalRecordDTO;
import com.hospital.model.Doctor;
import com.hospital.model.MedicalRecord;
import com.hospital.service.DoctorService;
import com.hospital.service.MedicalRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {

    private final MedicalRecordService service;

    public MedicalRecordController(MedicalRecordService service) {
        this.service = service;
    }

    @GetMapping
    public List<MedicalRecordDTO> getAll() {
        return service.getAll().stream()
                .map(r -> new MedicalRecordDTO(
                        r.getId(),
                        r.getPatient().getId(),
                        r.getDoctor().getId(),
                        r.getDiagnosis(),
                        r.getPrescription()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public MedicalRecord getById(@PathVariable Long id) {
        return service.getById(id).orElse(null);
    }

    @GetMapping("/patient/{patientId}")
    public List<MedicalRecord> getByPatient(@PathVariable Long patientId) {
        return service.getByPatientId(patientId);
    }

    @PostMapping
    public MedicalRecord create(@RequestBody MedicalRecord record) {
        return service.save(record);
    }

    @PutMapping("/{id}")
    public MedicalRecord update(@PathVariable Long id, @RequestBody MedicalRecord record) {
        record.setId(id);
        return service.save(record);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}