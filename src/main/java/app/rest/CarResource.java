package app.rest;

import app.bean.CarBean;
import app.bean.UserBean;
import app.model.Car;
import app.model.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/car")
public class CarResource {

    @Inject private CarBean carBean;
    @Inject private UserBean userBean;

    @Context
    private SecurityContext securityContext;  // JAX-RS SecurityContext, not Jakarta Security

    // ── Helper ────────────────────────────────────────────────

    private User resolveRestCaller() {
        if (securityContext.getUserPrincipal() == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Not authenticated.")
                            .build()
            );
        }
        String username = securityContext.getUserPrincipal().getName();
        User user = userBean.findByUsername(username);
        if (user == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("No user record for: " + username)
                            .build()
            );
        }
        return user;
    }

    // ── Endpoints ─────────────────────────────────────────────

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Car> getCars() {
        User caller = resolveRestCaller();
        return carBean.findAll(caller);  // scoped by role/showroom
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCar(Car car) {
        User caller = resolveRestCaller();
        carBean.create(car, caller);
        return Response.status(Response.Status.CREATED).entity(car).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Car findById(@PathParam("id") Long id) {
        resolveRestCaller(); // auth check only
        return carBean.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCar(@PathParam("id") Long id, Car car) {
        User caller = resolveRestCaller();
        car.setId(id);
        carBean.update(car, caller);
        return Response.ok(car).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCar(@PathParam("id") Long id) {
        User caller = resolveRestCaller();
        carBean.remove(id, caller);
        return Response.noContent().build();
    }
}
