package apivision.controllers;

import apivision.config.HibernateConfig;
import apivision.daos.AdoptionDAO;
import apivision.dtos.AdoptionDTO;
import apivision.entities.Adoption;
import dk.bugelhartmann.UserDTO;  // Assuming you have this UserDTO for user role information
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
/*
public class AdoptionController implements IController<AdoptionDTO, Integer> {

    private final AdoptionDAO dao;

    public AdoptionController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("petadoption");
        this.dao = AdoptionDAO.getInstance(emf);
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
        Adoption adoption = dao.read(id);
        AdoptionDTO adoptionDTO = AdoptionDTO.toDTO(adoption);
        ctx.status(200);
        ctx.json(adoptionDTO);
    }

    @Override
    public void readAll(Context ctx) {
        // All users can view the list of adoptions
        List<AdoptionDTO> adoptionDTOS = dao.readAll();
        ctx.status(200);
        ctx.json(adoptionDTOS);
    }

    @Override
    public void create(Context ctx) {
        // Check if user has ADMIN or MASTER role
        UserDTO user = ctx.attribute("user");
        if (!userHasRole(user, "ADMIN", "MASTER")) {
            System.out.println("User does not have permission to create a new adoption. Roles: " + user.getRoles());
            ctx.status(403).result("Forbidden: You do not have permission to create a new adoption.");
            return;
        }

        AdoptionDTO jsonRequest = ctx.bodyAsClass(AdoptionDTO.class);
        Adoption adoption = AdoptionDTO.toEntity(jsonRequest);
        AdoptionDTO adoptionDTO = dao.create(adoption);
        ctx.status(201);
        ctx.json(adoptionDTO);
    }

    @Override
    public void update(Context ctx) {
        // Check if user has ADMIN or MASTER role
        UserDTO user = ctx.attribute("user");
        if (!userHasRole(user, "ADMIN", "MASTER")) {
            System.out.println("User does not have permission to update this adoption. Roles: " + user.getRoles());
            ctx.status(403).result("Forbidden: You do not have permission to update this adoption.");
            return;
        }

        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        AdoptionDTO adoptionDTO = dao.update(id, validateEntity(ctx));
        ctx.status(200);
        ctx.json(adoptionDTO);
    }

    @Override
    public void delete(Context ctx) {
        // Check if user has ADMIN or MASTER role
        UserDTO user = ctx.attribute("user");
        if (!userHasRole(user, "ADMIN", "MASTER")) {
            System.out.println("User does not have permission to delete this adoption. Roles: " + user.getRoles());
            ctx.status(403).result("Forbidden: You do not have permission to delete this adoption.");
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
    public AdoptionDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(AdoptionDTO.class)
                .check(d -> d.getUsername() != null && !d.getUsername().isEmpty(), "User ID must be set")
                .check(d -> d.getDog() != null, "Dog must be set")
                .check(d -> d.getDate() != null, "Date must be set")
                .check(d -> d.getStatus() != null, "Status must be set")
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
}*/