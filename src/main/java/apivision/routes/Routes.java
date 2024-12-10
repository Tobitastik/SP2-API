package apivision.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
    private final DogRoutes dogRoutes = new DogRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/dogs", dogRoutes.getRoutes());
            path("/adoption", new AdoptionRoutes().getRoutes());
            path("/appointment", new AppointmentRoutes().getRoutes());
        };
    }
}
