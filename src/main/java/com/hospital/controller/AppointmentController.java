package com.hospital.controller;

import com.hospital.dto.AppointmentDTO;
import com.hospital.model.Appointment;
import com.hospital.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

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


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
