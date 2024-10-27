// src/main/java/apivision/controllers/AppointmentController.java

package apivision.controllers;

import apivision.config.HibernateConfig;
import apivision.daos.AppointmentDAO;
import apivision.dtos.AppointmentDTO;
import apivision.entities.Appointment;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class AppointmentController {

    private final AppointmentDAO dao;

    public AppointmentController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("petadoption");
        this.dao = AppointmentDAO.getInstance(emf);
    }

    public void readAll(Context ctx) {
        List<AppointmentDTO> appointmentDTOS = dao.readAll();
        ctx.status(200);
        ctx.json(appointmentDTOS);
    }

    public void read(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Appointment appointment = dao.read(id);
        AppointmentDTO appointmentDTO = AppointmentDTO.toDTO(appointment);
        ctx.status(200);
        ctx.json(appointmentDTO);
    }

    public void create(Context ctx) {
        AppointmentDTO jsonRequest = ctx.bodyAsClass(AppointmentDTO.class);
        Appointment appointment = AppointmentDTO.toEntity(jsonRequest);
        dao.create(appointment);
        ctx.status(201);
        ctx.json(jsonRequest);
    }

    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        AppointmentDTO jsonRequest = ctx.bodyAsClass(AppointmentDTO.class);
        Appointment appointment = AppointmentDTO.toEntity(jsonRequest);
        dao.update(appointment);
        ctx.status(200);
        ctx.json(jsonRequest);
    }

    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        dao.delete(id);
        ctx.status(204);
    }
}