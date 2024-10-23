package apivision.controllers;

import apivision.config.HibernateConfig;
import apivision.controllers.IController;
import apivision.daos.AppointmentDAO;
import apivision.dtos.AppointmentDTO;
import apivision.entities.Appointment;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AppointmentController implements IController<AppointmentDTO, Integer> {

    private final AppointmentDAO dao;

    public AppointmentController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = AppointmentDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx)  {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        AppointmentDTO appointmentDTO = AppointmentDTO.toDTO(dao.read(id));
        // response
        ctx.res().setStatus(200);
        ctx.json(appointmentDTO, AppointmentDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<AppointmentDTO> appointmentDTOS = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(appointmentDTOS, AppointmentDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        AppointmentDTO jsonRequest = ctx.bodyAsClass(AppointmentDTO.class);
        // DTO
        AppointmentDTO appointmentDTO = dao.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(appointmentDTO, AppointmentDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        AppointmentDTO appointmentDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(appointmentDTO, AppointmentDTO.class);
    }

    @Override
    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        // response
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public AppointmentDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(AppointmentDTO.class)
                .check( a -> a.getUserId() != null, "User ID must be set")
                .check( a -> a.getDog() != null, "Pet ID must be set")
                .check( a -> a.getDate() != null, "Date must be set")
                .check( a -> a.getStatus() != null, "Status must be set")
                .get();
    }

}