package apivision.routes;

import apivision.controllers.AppointmentController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class AppointmentRoutes {

    private final AppointmentController appointmentController = new AppointmentController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", appointmentController::create);
            get("/", appointmentController::readAll);
            get("/{id}", appointmentController::read);
            put("/{id}", appointmentController::update);
            delete("/{id}", appointmentController::delete);
        };
    }
}
