package apivision.entities;

import apivision.dtos.AppointmentDTO;
import apivision.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int petId;
    @Column(nullable = false)
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_status", nullable = false)
    private AppointmentStatus status;

    public Appointment(AppointmentDTO appointmentDTO){
        this.id = appointmentDTO.getId();
        this.userId = appointmentDTO.getUserId();
        this.petId = appointmentDTO.getPetId();
        this.date = appointmentDTO.getDate();
        this.status = appointmentDTO.getStatus();
    }
}