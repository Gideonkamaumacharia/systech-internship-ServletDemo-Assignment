package app.action.rest;

import app.bean.CarBean;
import app.bean.UserBean;
import app.model.Car;
import app.model.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/car")
public class CarResource {

    @Inject
    private CarBean carBean;

    @Inject
    private UserBean userBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Car> getCars(){
        return carBean.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCar(Car car){
        User currentUser = userBean.findById(1L);

        carBean.create(car,currentUser);

        return Response.status(Response.Status.CREATED)
                .entity(car)
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Car findById(@PathParam("id") Long id){
        return carBean.findById(id);
    }

    @PUT
    @Path("/{id}")
    public Response updateCar(@PathParam("id") Long id, Car car) {

        car.setId(id);

        carBean.update(car);

        return Response.ok(car).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCar(@PathParam("id") Long id) {

        carBean.remove(id);

        return Response.noContent().build();
    }
}
