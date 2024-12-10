package apivision.routes;

import apivision.controllers.AdoptionController;
import apivision.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class AdoptionRoutes {

    private final AdoptionController adoptionController = new AdoptionController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", adoptionController::create, Role.ADMIN);
            get("/", adoptionController::readAll, Role.USER);
            get("/{id}", adoptionController::read, Role.USER);
            put("/{id}", adoptionController::update, Role.ADMIN);
            delete("/{id}", adoptionController::delete, Role.ADMIN);
        };
    }
}