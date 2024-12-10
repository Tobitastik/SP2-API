package apivision.routes;

import apivision.controllers.AdoptionController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class AdoptionRoutes {

    private final AdoptionController adoptionController = new AdoptionController();

    public EndpointGroup getRoutes() {
        return () -> {
        post("/", adoptionController::create);
        get("/", adoptionController::readAll);
        get("/{id}", adoptionController::read);
        put("/{id}", adoptionController::update);
        delete("/{id}", adoptionController::delete);
        };
    }
}
