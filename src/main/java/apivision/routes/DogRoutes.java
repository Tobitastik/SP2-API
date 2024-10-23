package apivision.routes;

import apivision.controllers.DogController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DogRoutes {

    private final DogController dogController = new DogController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", dogController::create);  // Create a new dog
            get("/", dogController::readAll);   // Get all dogs
            get("/{id}", dogController::read);   // Get a specific dog by ID
            put("/{id}", dogController::update);  // Update a dog by ID
            delete("/{id}", dogController::delete);  // Delete a dog by ID
        };
    }
}
