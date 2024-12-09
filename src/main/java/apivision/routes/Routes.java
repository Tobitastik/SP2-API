package apivision.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
    private final DogRoutes dogRoutes = new DogRoutes();
    private final AppointmentRoutes appointmentRoutes = new AppointmentRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/dogs", dogRoutes.getRoutes());
            path("/appointments", appointmentRoutes.getRoutes());
        };
    }
}
