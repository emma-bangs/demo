package com.hospital.dto;

public record MedicalRecordDTO(
        Long id,
        Long patientId,
        Long doctorId,
        String diagnosis,
        String prescription
) {}
