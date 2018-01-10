package com.dixketl.pastelapp.AuxObjects;

/**
 * Created by juan on 10/01/18.
 */

public class EnCurso {

    private String idPastel;
    private String nombre;
    private String descripción;
    private String fechaPago;
    private String estatus;

    public EnCurso(){}
    public EnCurso(String idPastel,String nombre,String descripción,String fechaPago,String estatus){
        this.idPastel = idPastel;
        this.nombre = nombre;
        this.descripción = descripción;
        this.fechaPago = fechaPago;
        this.estatus = estatus;
    }

    public String getId() {
        return idPastel;
    }

    public void setId(String idPastel) {
        this.idPastel = idPastel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
