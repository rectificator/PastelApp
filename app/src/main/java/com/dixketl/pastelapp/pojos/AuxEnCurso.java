package com.dixketl.pastelapp.pojos;

/**
 * Created by dgaco on 10/01/18.
 */

public class AuxEnCurso {

    private String id;
    private String imagen;
    private String nombre;
    private String descripcion;
    private String fechaPago;
    private String progreso;

    public AuxEnCurso(String id,String imagen, String nombre, String descripcion, String fechaPago, String progreso){

        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaPago = fechaPago;
        this.progreso = progreso;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getProgreso() {
        return progreso;
    }

    public void setProgreso(String progreso) {
        this.progreso = progreso;
    }




}
