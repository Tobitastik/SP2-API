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
        Appointment appointment = appointmentDAO.read(id);

        if (appointment == null) {
            ctx.status(404).result("Appointment not found");
            return;
        }

        AppointmentDTO appointmentDTO = AppointmentDTO.toDTO(appointment);
        ctx.status(200).json(appointmentDTO);
    }

    public void create(Context ctx) {
        AppointmentDTO jsonRequest = ctx.bodyAsClass(AppointmentDTO.class);

        // Fetch the DogDTO using DogDAO
        DogDTO dogDTO = dogDAO.read(jsonRequest.getDogId());
        if (dogDTO == null) {
            ctx.status(404).result("Dog not found");
            return;
        }

        // Convert DogDTO to Dog entity
        Dog dog = DogDTO.convertToEntity(dogDTO);

        Appointment appointment = AppointmentDTO.toEntity(jsonRequest, dog);
        appointmentDAO.create(appointment);
        ctx.status(201).json(AppointmentDTO.toDTO(appointment));
    }


    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        AppointmentDTO jsonRequest = ctx.bodyAsClass(AppointmentDTO.class);

        // Ensure the appointment exists
        Appointment existingAppointment = appointmentDAO.read(id);
        if (existingAppointment == null) {
            ctx.status(404).result("Appointment not found");
            return;
        }

        // Fetch the DogDTO using DogDAO
        DogDTO dogDTO = dogDAO.read(jsonRequest.getDogId());
        if (dogDTO == null) {
            ctx.status(404).result("Dog not found");
            return;
        }

        // Convert DogDTO to Dog entity
        Dog dog = DogDTO.convertToEntity(dogDTO);

        Appointment updatedAppointment = AppointmentDTO.toEntity(jsonRequest, dog);
        appointmentDAO.update(updatedAppointment);
        ctx.status(200).json(AppointmentDTO.toDTO(updatedAppointment));
    }

    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Appointment appointment = appointmentDAO.read(id);
        if (appointment == null) {
            ctx.status(404).result("Appointment not found");
            return;
        }

        appointmentDAO.delete(id);
        ctx.status(204);
    }
}