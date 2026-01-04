package com.hospital.dto;

import java.time.LocalDateTime;

public record AppointmentDTO(
        Long id,
        LocalDateTime appointmentDate,
        Long doctorId,
        Long patientId,
        String status
) {}
