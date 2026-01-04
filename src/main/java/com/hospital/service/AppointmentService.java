package com.hospital.service;

import com.hospital.exceptions.AppointmentConflictException;
import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;
    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;

    public AppointmentService(AppointmentRepository repo,
                              DoctorRepository doctorRepo,
                              PatientRepository patientRepo) {
        this.repo = repo;
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
    }

    public Appointment save(Appointment appointment) {

        // Validate doctor exists
        Doctor doctor = doctorRepo.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        // Validate patient exists
        Patient patient = patientRepo.findById(appointment.getPatient().getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        LocalDateTime start = appointment.getAppointmentDate();
        LocalDateTime end = start.plusMinutes(30);

        // Doctor conflict check
        List<Appointment> doctorConflicts = repo.findByDoctorAndAppointmentDateBetween(doctor, start, end);
        if (!doctorConflicts.isEmpty()) {
            throw new AppointmentConflictException("Doctor already has an appointment at this time");
        }

        // Patient conflict check
        List<Appointment> patientConflicts = repo.findByPatientId(patient.getId()).stream()
                .filter(a -> !a.getAppointmentDate().isBefore(start) && !a.getAppointmentDate().isAfter(end))
                .toList();
        if (!patientConflicts.isEmpty()) {
            throw new AppointmentConflictException("Patient already has an appointment at this time");
        }

        return repo.save(appointment);
    }



    public List<Appointment> getAll() {
        return repo.findAll();
    }

    public Optional<Appointment> getById(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Appointment> getAppointmentsByDoctorAndDate(Doctor doctor, LocalDateTime start, LocalDateTime end) {
        return repo.findByDoctorAndAppointmentDateBetween(doctor, start, end);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return repo.findByPatientId(patientId);
    }
}
