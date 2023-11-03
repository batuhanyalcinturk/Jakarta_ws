package com.graysan.jakarta.controller;

import com.graysan.jakarta.model.Ogretmen;
import com.graysan.jakarta.repository.OgretmenRepository;
import jakarta.ejb.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/ogretmen")
@Singleton
public class OgretmenController {

    // 1- ? ile verilen query parameter
    // 2- / ile verilen path parameter
    // 3- header

    private final OgretmenRepository ogretmenRepository = new OgretmenRepository();

    @GET
    @Path(value = "/getall")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getAll(){
        // localhost:8080/jakartaweb/ogretmen/getall
        try {
            ArrayList<Ogretmen> result = ogretmenRepository.getAll();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.serverError().entity("bir hata oluştu").build();
        }
    }

    // 1 - QueryParam
    // Query ile ?id yazarak
    @GET
    @Path(value = "/getbyidqueryparam")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getByIdQuery(@QueryParam(value = "id") long id){

        // localhost:8080/jakartaweb/ogretmen/getbyidqueryparam?id=1
        try {
            Ogretmen result = ogretmenRepository.getByID(id);
            if (result == null){
                return Response.status(Response.Status.NOT_FOUND).entity("Kayit Bulunamadi.").build();
            }else
            {
                return Response.ok().entity(result).build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("bir hata oluştu").build();
        }
    }

    // 2- PathParam
    // endpoint içerisine / {} yazarak
    @GET
    @Path(value = "/getbyid/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getById(@PathParam(value = "id") long id){

        // localhost:8080/jakartaweb/ogretmen/getbyid/1
        try {
            Ogretmen result = ogretmenRepository.getByID(id);
            if (result == null){
                return Response.status(Response.Status.NOT_FOUND).entity("Kayit Bulunamadi.").build();
            }else
            {
                return Response.ok().entity(result).build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("bir hata oluştu").build();
        }
    }

    // 3 - Header
    // Postman ile header kısmından key ve value girilerek istek atılarak
    @GET
    @Path(value = "/getbyidheader")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getByIdHeader(@HeaderParam(value = "id") long id){

        // localhost:8080/jakartaweb/ogretmen/getbyidheader
        try {
            Ogretmen result = ogretmenRepository.getByID(id);
            if (result == null){
                return Response.status(Response.Status.NOT_FOUND).entity("Kayit Bulunamadi.").build();
            }else
            {
                return Response.ok().entity(result).build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("bir hata oluştu").build();
        }
    }

    @DELETE
    @Path(value = "/deletebyid/{id}")
    @Produces(value = MediaType.TEXT_PLAIN)
    public Response deleteById(@PathParam(value = "id") long id){
        // localhost:8080/jakartaweb/ogretmen/deletebyid/2
        try {
            if (ogretmenRepository.deleteByID(id)){
                return Response.ok().entity("Başarı ile silindi.").build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity("Kayit Bulunamadi.").build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("bir hata oluştu").build();
        }

    }

    @POST
    @Path(value = "save")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response save(Ogretmen ogr)
    {
        try
        {
            if (ogretmenRepository.save(ogr)) {
                return Response.status(Response.Status.CREATED).entity("Başarı ile kaydedildi").build();
            } else {
                return Response.serverError().entity("Başarı ile kaydedilemedi").build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("Bir hata oluştu -> " + e.getClass()).build();
        }
    }
}
