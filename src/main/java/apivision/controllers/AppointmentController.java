package apivision.controllers;

import apivision.config.HibernateConfig;
import apivision.daos.AppointmentDAO;
import apivision.daos.DogDAO;
import apivision.dtos.AppointmentDTO;
import apivision.dtos.DogDTO;
import apivision.entities.Appointment;
import apivision.entities.Dog;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import apivision.enums.AppointmentStatus;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentController {

    private final AppointmentDAO appointmentDAO;
    private final DogDAO dogDAO;

    public AppointmentController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("petadoption");
        this.appointmentDAO = AppointmentDAO.getInstance(emf);
        this.dogDAO = DogDAO.getInstance(emf);
    }

    public void readAll(Context ctx) {
        List<AppointmentDTO> appointmentDTOS = appointmentDAO.readAll();
        ctx.status(200).json(appointmentDTOS);
    }

    public void read(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        AppointmentDTO appointmentDTO = appointmentDAO.read(id);

        if (appointmentDTO == null) {
            ctx.status(404).result("Appointment not found");
            return;
        }

        ctx.status(200).json(appointmentDTO);
    }

    public void create(Context ctx) {
        AppointmentDTO jsonRequest = ctx.bodyAsClass(AppointmentDTO.class);

        // Fetch the Dog entity
        DogDTO dogDTO = dogDAO.read(jsonRequest.getDogId());
        Dog dog = DogDTO.convertToEntity(dogDTO);
        if (dog == null) {
            ctx.status(404).result("Dog not found");
            return;
        }

        Appointment appointment = new Appointment();
        appointment.setUsername(jsonRequest.getUsername());
        appointment.setDog(dog);
        appointment.setDate(jsonRequest.getDate());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        appointmentDAO.create(appointment);

        ctx.status(201).json(AppointmentDTO.toDTO(appointment));
    }



    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        AppointmentDTO appointmentDTO = appointmentDAO.read(id);
        if (appointmentDTO == null) {
            ctx.status(404).result("Appointment not found");
            return;
        }

        appointmentDAO.delete(id);
        ctx.status(204);
    }

    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        AppointmentDTO jsonRequest = ctx.bodyAsClass(AppointmentDTO.class);

        AppointmentDTO existingAppointmentDTO = appointmentDAO.read(id);
        if (existingAppointmentDTO == null) {
            ctx.status(404).result("Appointment not found");
            return;
        }
    }
}