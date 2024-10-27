package apivision.entities;

import apivision.dtos.AppointmentDTO;
import apivision.enums.AppointmentStatus;
import apivision.security.entitiess.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "dog")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @JoinColumn(name = "username", nullable = false)
    private String username;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "dog_id", nullable = false)
    private Dog dog;

    @Column(nullable = false)
    private LocalDate date;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment appointment = (Appointment) o;
        return id == appointment.id &&
                Objects.equals(username, appointment.username) &&
                Objects.equals(dog, appointment.dog) &&
                Objects.equals(date, appointment.date) &&
                status == appointment.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, date, status);
    }

}
