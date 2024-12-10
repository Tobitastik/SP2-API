package apivision.dtos;

import apivision.entities.Appointment;
import apivision.entities.Dog;
import apivision.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private int id;
    private String username;
    private int dogId;
    private LocalDate date;
    private AppointmentStatus status;

    public AppointmentDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.username = appointment.getUsername();
        this.dogId = appointment.getDog().getId();
        this.date = appointment.getDate();
        this.status = appointment.getStatus();
    }

    public static AppointmentDTO toDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getUsername(),
                appointment.getDog().getId(),
                appointment.getDate(),
                appointment.getStatus()
        );
    }

    public static Appointment toEntity(AppointmentDTO appointmentDTO, Dog dog) {
        return Appointment.builder()
                .id(appointmentDTO.getId())
                .username(appointmentDTO.getUsername())
                .dog(dog)
                .date(appointmentDTO.getDate())
                .status(appointmentDTO.getStatus())
                .build();
    }

    public static List<AppointmentDTO> toDTOList(List<Appointment> appointmentList) {
        return appointmentList.stream()
                .map(AppointmentDTO::toDTO)
                .collect(Collectors.toList());
    }
}