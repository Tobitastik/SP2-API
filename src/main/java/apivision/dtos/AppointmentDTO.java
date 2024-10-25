package apivision.dtos;

import apivision.entities.Appointment;
import apivision.entities.Dog;
import apivision.enums.AppointmentStatus;
import apivision.security.entitiess.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private int id;
    private String username;
    private Dog dog;
    private LocalDateTime date;
    private AppointmentStatus status;

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.username = appointment.getUsername();
        this.dog = appointment.getDog();
        this.date = appointment.getDate();
        this.status = appointment.getStatus();
    }

    public static AppointmentDTO toDTO(Appointment appointment) {
        return new AppointmentDTO(appointment.getId(), appointment.getUsername(), appointment.getDog(), appointment.getDate(), appointment.getStatus());
    }

    public static Appointment toEntity(AppointmentDTO appointmentDTO) {
        return new Appointment(appointmentDTO);
    }

    public static List<AppointmentDTO> toDTOList(List<Appointment> appointmentList) {
        return appointmentList.stream().map(AppointmentDTO::toDTO).collect(Collectors.toList());
    }
}