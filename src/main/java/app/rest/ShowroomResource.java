package app.rest;

import app.bean.ShowroomBean;
import app.bean.UserBean;
import app.model.Showroom;
import app.model.User;
import app.model.enums.UserRole;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/showroom")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShowroomResource {

    @Inject
    private ShowroomBean showroomBean;

    @Inject
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;



    private User resolveRestCaller(){

        if(securityContext.getUserPrincipal() == null){

            throw new WebApplicationException(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Not authenticated.")
                            .build()
            );
        }

        String username = securityContext.getUserPrincipal().getName();

        User user = userBean.findByUsername(username);

        if(user == null){

            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("User not found.")
                            .build()
            );
        }

        return user;
    }

    // ─────────────────────────────────────────────
    // GET ALL SHOWROOMS
    // ─────────────────────────────────────────────

    @GET
    public List<Showroom> findAll(){

        User caller = resolveRestCaller();
        if(caller.getRole() == UserRole.ADMIN){
            return showroomBean.getShowrooms();
        }

        return List.of(caller.getShowroom());
    }

    // ─────────────────────────────────────────────
    // GET SHOWROOM BY ID
    // ─────────────────────────────────────────────

    @GET
    @Path("/{id}")
    public Showroom findById(
            @PathParam("id") Long id){

        User caller = resolveRestCaller();

        Showroom showroom =
                showroomBean.findById(id);

        if(showroom == null){

            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("Showroom not found.")
                            .build()
            );
        }

        // ADMIN can access all
        if(caller.getRole() == UserRole.ADMIN){
            return showroom;
        }

        // MANAGER only own showroom
        if(!caller.getShowroom()
                .getId()
                .equals(showroom.getId())){

            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("Access denied.")
                            .build()
            );
        }

        return showroom;
    }

    // ─────────────────────────────────────────────
    // CREATE SHOWROOM
    // ─────────────────────────────────────────────

    @POST
    public Response create(
            Showroom showroom){

        User caller = resolveRestCaller();

        if(caller.getRole() != UserRole.ADMIN){

            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("Admins only.")
                            .build()
            );
        }

        showroomBean.createShowroom(showroom,caller);

        return Response.status(Response.Status.CREATED)
                .entity(showroom)
                .build();
    }

    // ─────────────────────────────────────────────
    // UPDATE SHOWROOM
    // ─────────────────────────────────────────────

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") Long id,
            Showroom showroom){

        User caller = resolveRestCaller();

        if(caller.getRole() != UserRole.ADMIN){

            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("Admins only.")
                            .build()
            );
        }

        showroom.setId(id);

        showroomBean.update(showroom);

        return Response.ok(showroom)
                .build();
    }

    // ─────────────────────────────────────────────
    // DELETE SHOWROOM
    // ─────────────────────────────────────────────

    @DELETE
    @Path("/{id}")
    public Response delete(
            @PathParam("id") Long id){

        User caller = resolveRestCaller();

        if(caller.getRole() != UserRole.ADMIN){

            throw new WebApplicationException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity("Admins only.")
                            .build()
            );
        }

        showroomBean.remove(id,caller);

        return Response.noContent()
                .build();
    }
}