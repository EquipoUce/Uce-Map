package com.example.ucemap.repository.modelo;

import java.util.List;

public class Facultad {
    private String nombre;
    private List<String> servicios;
    private List<String> imagenes;
    private String informacion;
    private List<String> detallesEntradas;
    private Double latitud;
    private Double longitud;

    public static final String[] FACULTAD_ATRIBUTOS_JASON = new String[]{"nombre", "servicios","imagenes", "informacion","descripcion","latitud", "longitud"};

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getServicios() {
        return servicios;
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Facultad{" +
                "nombre='" + nombre + '\'' +
                ", servicios=" + servicios +
                ", imagenes=" + imagenes +
                ", descripcion='" + informacion + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }


    public List<String> getDetallesEntradas() {
        return detallesEntradas;
    }

    public void setDetallesEntradas(List<String> detallesEntradas) {
        this.detallesEntradas = detallesEntradas;
    }
}
