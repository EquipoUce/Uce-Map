package com.example.ucemap.repository.modelo;

public class Posicion {
    private String identificador;
    private Double latitud;
    private Double longitud;

    public static final String[] ENTRADAS_ATRIBUTOS_JASON = new String[]{"identificador","latitud","longitud"};
    public Posicion() {
    }

    public Posicion(String identificador, Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.identificador = identificador;
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public String toString() {
        return "Posicion{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}