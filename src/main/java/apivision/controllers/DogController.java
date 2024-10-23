package apivision.controllers;

import apivision.config.HibernateConfig;
import apivision.daos.DogDAO;
import apivision.dtos.DogDTO;
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
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        DogDTO dogDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(dogDTO, DogDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        List<DogDTO> dogDTOS = dao.readAll();
        ctx.res().setStatus(200);
        ctx.json(dogDTOS, DogDTO.class);
    }

    @Override
    public void create(Context ctx) {
        DogDTO jsonRequest = ctx.bodyAsClass(DogDTO.class);
        DogDTO dogDTO = dao.create(jsonRequest);
        ctx.res().setStatus(201);
        ctx.json(dogDTO, DogDTO.class);
    }

    @Override
    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        DogDTO dogDTO = dao.update(id, validateEntity(ctx));
        ctx.res().setStatus(200);
        ctx.json(dogDTO, DogDTO.class);
    }

    @Override
    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        ctx.res().setStatus(204);
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
}
