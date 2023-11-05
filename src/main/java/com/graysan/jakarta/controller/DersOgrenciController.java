package com.graysan.jakarta.controller;

import com.graysan.jakarta.model.DersOgrenci;
import com.graysan.jakarta.model.Ogrenci;
import com.graysan.jakarta.repository.DersOgrenciRepository;
import com.graysan.jakarta.repository.OgrenciRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/dersogrenci")
public class DersOgrenciController {

    private final DersOgrenciRepository dersOgrenciRepository = new DersOgrenciRepository();

    @GET
    @Path(value = "/getall")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getAll(){

        // localhost:8080/jakartaweb/dersogrenci/getall
        try {
            ArrayList<DersOgrenci> result = dersOgrenciRepository.getAll();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.serverError().entity("bir hata oluştu").build();
        }
    }

    @GET
    @Path(value = "/getbyid/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getById(@PathParam(value = "id") long id){

        // localhost:8080/jakartaweb/dersogrenci/getbyid/1
        try {
            DersOgrenci result = dersOgrenciRepository.getByID(id);
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
            if (dersOgrenciRepository.deleteByID(id)){
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
    public Response save(DersOgrenci dersOgr)
    {
        try
        {
            if (dersOgrenciRepository.save(dersOgr)) {
                return Response.status(Response.Status.CREATED).entity("Başarı ile kaydedildi").build();
            } else {
                return Response.serverError().entity("Başarı ile kaydedilemedi").build();
            }
        } catch (Exception e) {
            return Response.serverError().entity("Bir hata oluştu -> " + e.getClass()).build();
        }
    }
}
