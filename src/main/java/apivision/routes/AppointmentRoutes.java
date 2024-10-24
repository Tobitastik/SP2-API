package apivision.routes;

import apivision.controllers.AppointmentController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class AppointmentRoutes {
    private final AppointmentController appointmentController = new AppointmentController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", appointmentController::create);  // Only ADMIN and MASTER can create
            get("/", appointmentController::readAll);   // All users can view all dogs
            get("/{id}", appointmentController::read);   // Only ADMIN and MASTER can get dog by ID
            put("/{id}", appointmentController::update);  // Only ADMIN and MASTER can update
            delete("/{id}", appointmentController::delete);  // Only ADMIN and MASTER can delete
        };
    }
}
