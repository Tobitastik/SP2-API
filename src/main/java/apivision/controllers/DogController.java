package apivision.controllers;

import apivision.config.HibernateConfig;
import apivision.daos.DogDAO;
import apivision.dtos.DogDTO;
import dk.bugelhartmann.UserDTO;  // Assuming you have this UserDTO for user role information
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DogController implements IController<DogDTO, Integer> {

    private final DogDAO dao;

    public DogController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("petadoption");
        this.dao = DogDAO.getInstance(emf);
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
        DogDTO dogDTO = dao.read(id);
        ctx.status(200);
        ctx.json(dogDTO);
    }

    @Override
    public void readAll(Context ctx) {
        // All users can view the list of dogs
        List<DogDTO> dogDTOS = dao.readAll();
        ctx.status(200);
        ctx.json(dogDTOS);
    }

    @Override
    public void create(Context ctx) {
        // Check if user has ADMIN or MASTER role
        UserDTO user = ctx.attribute("user");
        if (!userHasRole(user, "ADMIN", "MASTER")) {
            System.out.println("User does not have permission to create a new dog. Roles: " + user.getRoles());
            ctx.status(403).result("Forbidden: You do not have permission to create a new dog.");
            return;
        }

        DogDTO jsonRequest = ctx.bodyAsClass(DogDTO.class);
        DogDTO dogDTO = dao.create(jsonRequest);
        ctx.status(201);
        ctx.json(dogDTO);
    }

    @Override
    public void update(Context ctx) {
        // Check if user has ADMIN or MASTER role
        UserDTO user = ctx.attribute("user");
        if (!userHasRole(user, "ADMIN", "MASTER")) {
            System.out.println("User does not have permission to update this dog. Roles: " + user.getRoles());
            ctx.status(403).result("Forbidden: You do not have permission to update this dog.");
            return;
        }

        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        DogDTO dogDTO = dao.update(id, validateEntity(ctx));
        ctx.status(200);
        ctx.json(dogDTO);
    }

    @Override
    public void delete(Context ctx) {
        // Check if user has ADMIN or MASTER role
        UserDTO user = ctx.attribute("user");
        if (!userHasRole(user, "ADMIN", "MASTER")) {
            System.out.println("User does not have permission to delete this dog. Roles: " + user.getRoles());
            ctx.status(403).result("Forbidden: You do not have permission to delete this dog.");
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
    public DogDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(DogDTO.class)
                .check(d -> d.getName() != null && !d.getName().isEmpty(), "Dog name must be set")
                .check(d -> d.getBreed() != null && !d.getBreed().isEmpty(), "Dog breed must be set")
                .check(d -> d.getAge() != null && d.getAge() > 0, "Dog age must be a positive number")
                .check(d -> d.getStatus() != null, "Dog status must be set")
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
