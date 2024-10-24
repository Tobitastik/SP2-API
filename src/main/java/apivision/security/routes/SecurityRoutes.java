package apivision.security.routes;

import apivision.routes.DogRoutes;
import com.fasterxml.jackson.databind.ObjectMapper;
import apivision.utils.Utils;
import apivision.security.controllers.SecurityController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Purpose: To handle security in the API
 * Author: Thomas Hartmann
 */
public class SecurityRoutes {
    private static final ObjectMapper jsonMapper = new Utils().getObjectMapper();
    private static final SecurityController securityController = SecurityController.getInstance();

    public static EndpointGroup getSecurityRoutes() {
        return () -> {
            path("/auth", () -> {
                get("/test", ctx -> ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from Open")));
                post("/login", securityController.login());
                post("/register", securityController.register());
                post("/user/addrole", securityController.addRole()); // This might be an admin route, check authorization separately
            });
        };
    }

    public static EndpointGroup getSecuredRoutes() {
        return () -> {
            path("/protected", () -> {
                get("/user_demo", ctx -> ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from USER Protected")));
                get("/admin_demo", ctx -> ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from ADMIN Protected")));
                get("/master_demo", ctx -> ctx.json(jsonMapper.createObjectNode().put("msg", "Hello from MASTER Protected")));
            });
        };
    }

    private final DogRoutes dogRoutes = new DogRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/dogs", dogRoutes.getRoutes());  // All dog-related routes are protected based on user roles
        };
    }
}
