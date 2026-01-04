package com.hospital.controller;

import com.hospital.dto.AppointmentDTO;
import com.hospital.model.Appointment;
import com.hospital.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
// initializing the appointment service
    private final AppointmentService service;
// constructor for initializing attribute
    public AppointmentController(AppointmentService service) {
        this.service = service;
    }
// mapping for the CRUD(Create, Read, Update, & Delete) operations
    // endpoint for reading all appointments
    @GetMapping
    public List<AppointmentDTO> getAll() {
        return service.getAll().stream()
                .map(a -> new AppointmentDTO(
                        a.getId(),
                        a.getAppointmentDate(),
                        a.getDoctor().getId(),
                        a.getPatient().getId(),
                        a.getStatus()
                ))
                .toList();
    }

    // endpoint for reading an appointment by ID
    @GetMapping("/{id}")
    public AppointmentDTO getById(@PathVariable Long id) {
        return service.getById(id)
                .map(a -> new AppointmentDTO(
                        a.getId(),
                        a.getAppointmentDate(),
                        a.getDoctor().getId(),
                        a.getPatient().getId(),
                        a.getStatus()
                ))
                .orElse(null);
    }


    // endpoint for booking an appointment
    @PostMapping
    public AppointmentDTO create(@RequestBody Appointment appointment) {
        Appointment saved = service.save(appointment);
        return new AppointmentDTO(
                saved.getId(),
                saved.getAppointmentDate(),
                saved.getDoctor().getId(),
                saved.getPatient().getId(),
                saved.getStatus()
        );
    }

    // endpoint for updating an appointment with a given ID
    @PutMapping("/{id}")
    public AppointmentDTO update(@PathVariable Long id, @RequestBody Appointment appointment) {
        appointment.setId(id);
        Appointment saved = service.save(appointment);
        return new AppointmentDTO(
                saved.getId(),
                saved.getAppointmentDate(),
                saved.getDoctor().getId(),
                saved.getPatient().getId(),
                saved.getStatus()
        );
    }

    // endpoint for deleting an appointment using its ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
