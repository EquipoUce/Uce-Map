package com.example.ucemap.repository.modelo;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.List;

public class InformacionGeneral {
    private Posicion posicion;
    private List<Descripcion> descripcion;
    private List<CarouselItem> imagenes;
    private List<String> detalleEntradas;

    public InformacionGeneral() {
    }
    public InformacionGeneral(Posicion posicion, List<Descripcion> descripcion, List<CarouselItem> imagenes, List <String> detalleEntradas) {
        this.posicion = posicion;
        this.descripcion = descripcion;
        this.imagenes = imagenes;
        this.detalleEntradas = detalleEntradas;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public List<Descripcion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<Descripcion> descripcion) {
        this.descripcion = descripcion;
    }

    public List<CarouselItem> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<CarouselItem> imagenes) {
        this.imagenes = imagenes;
    }

    public List<String> getDetalleEntradas() {
        return detalleEntradas;
    }

    public void setDetalleEntradas(List<String> detalleEntradas) {
        this.detalleEntradas = detalleEntradas;
    }
}


