package com.graysan.jakarta.controller;

import com.graysan.jakarta.model.Ogrenci;
import com.graysan.jakarta.repository.OgrenciRepository;
import jakarta.ejb.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/ogrenci")
@Singleton
public class OgrenciController {

    private final OgrenciRepository ogrenciRepository = new OgrenciRepository();

    @GET
    @Path(value = "/getall")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getAll(){

        // localhost:8080/jakartaweb/ogrenci/getall
        try {
            ArrayList<Ogrenci> result = ogrenciRepository.getAll();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.serverError().entity("bir hata oluştu").build();
        }
    }

    @GET
    @Path(value = "/getbyid/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getById(@PathParam(value = "id") long id){

        // localhost:8080/jakartaweb/ogrenci/getbyid/1
        try {
            Ogrenci result = ogrenciRepository.getByID(id);
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

        // localhost:8080/jakartaweb/ogrenci/deletebyid/2
        try {
            if (ogrenciRepository.deleteByID(id)){
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
    public Response save(Ogrenci ogr)
    {
        try
        {
            if (ogrenciRepository.save(ogr)) {
                return Response.status(Response.Status.CREATED).entity("Başarı ile kaydedildi").build();
            } else {
                return Response.serverError().entity("Başarı ile kaydedilemedi").build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("Bir hata oluştu -> " + e.getClass()).build();
        }
    }
}
