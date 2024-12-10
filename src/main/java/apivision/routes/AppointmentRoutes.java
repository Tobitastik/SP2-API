package apivision.routes;

import apivision.controllers.AppointmentController;
import apivision.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class AppointmentRoutes {

    private final AppointmentController appointmentController = new AppointmentController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", appointmentController::create, Role.ADMIN);
            get("/", appointmentController::readAll, Role.USER);
            get("/{id}", appointmentController::read, Role.USER);
            put("/{id}", appointmentController::update, Role.ADMIN);
            delete("/{id}", appointmentController::delete, Role.ADMIN);
        };
    }
}