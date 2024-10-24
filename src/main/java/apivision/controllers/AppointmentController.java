package apivision.controllers;

import apivision.config.HibernateConfig;
import apivision.daos.AppointmentDAO;
import apivision.dtos.AppointmentDTO;
import dk.bugelhartmann.UserDTO;  // Assuming you have this UserDTO for user role information
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class AppointmentController implements IController<AppointmentDTO, Integer> {

    private final AppointmentDAO dao;

    public AppointmentController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("petadoption");
        this.dao = AppointmentDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        // Check if user has ADMIN or MASTER role
        UserDTO user = ctx.attribute("user");
        if (!userHasRole(user, "ADMIN", "MASTER")) {
            System.out.println("User does not have permission to access this resource. Roles: " + user.getRoles());
            ctx.status(403).result("Forbidden: You do not have permission to access this resource.");
            return;
        }

        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        AppointmentDTO appointmentDTO = dao.read(id);
        ctx.status(200);
        ctx.json(appointmentDTO);
    }

    @Override
    public void readAll(Context ctx) {
        // All users can view the list of appointments
        List<AppointmentDTO> appointmentDTOS = dao.readAll();
        ctx.status(200);
        ctx.json(appointmentDTOS);
    }

    @Override
    public void create(Context ctx) {
        // Check if user has ADMIN or MASTER role
        UserDTO user = ctx.attribute("user");
        if (!userHasRole(user, "ADMIN", "MASTER")) {
            System.out.println("User does not have permission to create a new appointment. Roles: " + user.getRoles());
            ctx.status(403).result("Forbidden: You do not have permission to create a new appointment.");
            return;
        }

        AppointmentDTO jsonRequest = ctx.bodyAsClass(AppointmentDTO.class);
        AppointmentDTO appointmentDTO = dao.create(jsonRequest);
        ctx.status(201);
        ctx.json(appointmentDTO);
    }

    @Override
    public void update(Context ctx) {
        // Check if user has ADMIN or MASTER role
        UserDTO user = ctx.attribute("user");
        if (!userHasRole(user, "ADMIN", "MASTER")) {
            System.out.println("User does not have permission to update this appointment. Roles: " + user.getRoles());
            ctx.status(403).result("Forbidden: You do not have permission to update this appointment.");
            return;
        }

        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        AppointmentDTO appointmentDTO = dao.update(id, validateEntity(ctx));
        ctx.status(200);
        ctx.json(appointmentDTO);
    }

    @Override
    public void delete(Context ctx) {
        // Check if user has ADMIN or MASTER role
        UserDTO user = ctx.attribute("user");
        if (!userHasRole(user, "ADMIN", "MASTER")) {
            System.out.println("User does not have permission to delete this appointment. Roles: " + user.getRoles());
            ctx.status(403).result("Forbidden: You do not have permission to delete this appointment.");
            return;
        }

        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        ctx.status(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public AppointmentDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(AppointmentDTO.class)
                .check(d -> d.getDate() != null, "Appointment date and time must be set")
                .check(d -> d.getStatus() != null, "Appointment status must be set")
                .get();
    }

    // Helper method to check if the user has the required role
    private boolean userHasRole(UserDTO user, String... roles) {
        for (String role : roles) {
            if (user.getRoles().contains(role)) {
                return true;
            }
        }
        return false;
    }
}