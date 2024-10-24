package apivision.routes;

import apivision.controllers.AdoptionController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class AdoptionRoutes {
    private final AdoptionController adoptionController = new AdoptionController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", adoptionController::create);  // Only ADMIN and MASTER can create
            get("/", adoptionController::readAll);   // All users can view all dogs
            get("/{id}", adoptionController::read);   // Only ADMIN and MASTER can get dog by ID
            put("/{id}", adoptionController::update);  // Only ADMIN and MASTER can update
            delete("/{id}", adoptionController::delete);  // Only ADMIN and MASTER can delete
        };
    }
}
