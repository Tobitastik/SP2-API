package apivision.routes;

import apivision.controllers.DogController;
import apivision.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DogRoutes {

    private final DogController dogController = new DogController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", dogController::create, Role.ADMIN);
            get("/", dogController::readAll, Role.ANYONE);
            get("/{id}", dogController::read, Role.ANYONE);
            put("/{id}", dogController::update, Role.ADMIN);
            delete("/{id}", dogController::delete, Role.ADMIN);
        };
    }
}
