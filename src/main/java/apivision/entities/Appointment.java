package apivision.entities;

import apivision.dtos.AppointmentDTO;
import apivision.enums.AppointmentStatus;
import apivision.security.entitiess.User;
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


    @JoinColumn(name = "username", nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "dog_id", nullable = false)  // Use the correct name for the foreign key
    private Dog dog;

    @Column(nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_status", nullable = false)
    private AppointmentStatus status;

    public Appointment(AppointmentDTO appointmentDTO){
        this.id = appointmentDTO.getId();
        this.username = appointmentDTO.getUsername();
        this.dog = appointmentDTO.getDog();
        this.date = appointmentDTO.getDate();
        this.status = appointmentDTO.getStatus();
    }
}
