package apivision.routes;

import apivision.controllers.DogController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DogRoutes {

    private final DogController dogController = new DogController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", dogController::create);  // Only ADMIN and MASTER can create
            get("/", dogController::readAll);   // All users can view all dogs
            get("/{id}", dogController::read);   // Only ADMIN and MASTER can get dog by ID
            put("/{id}", dogController::update);  // Only ADMIN and MASTER can update
            delete("/{id}", dogController::delete);  // Only ADMIN and MASTER can delete
        };
    }
}
