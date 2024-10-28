package apivision.routes;

import apivision.controllers.DogController;
import apivision.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DogRoutes {

    private final DogController dogController = new DogController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", dogController::create);
            get("/", dogController::readAll);
            get("/{id}", dogController::read);
            put("/{id}", dogController::update);
            delete("/{id}", dogController::delete);
        };
    }
}
