package com.example.ucemap.service.informacionSingleton;

import com.example.ucemap.repository.modelo.InformacionGeneral;
import com.example.ucemap.repository.modelo.Posicion;

import java.util.List;

public class EntradasHolder {
    private static EntradasHolder instanciaUnica;
    private static boolean informacionInicializada = false;
    private static List<Posicion> entradasPosiciones;
    private EntradasHolder() {}
    public static EntradasHolder obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new EntradasHolder();
        }
        return instanciaUnica;
    }

    public static EntradasHolder getInstanciaUnica() {
        return instanciaUnica;
    }

    public static void setInstanciaUnica(EntradasHolder instanciaUnica) {
        EntradasHolder.instanciaUnica = instanciaUnica;
    }

    public static boolean getInformacionInicializada() {
        return informacionInicializada;
    }

    public static void setInformacionInicializada(boolean informacionInicializada) {
        EntradasHolder.informacionInicializada = informacionInicializada;
    }

    public static List<Posicion> getEntradasPosiciones() {
        return entradasPosiciones;
    }

    public static void setEntradasPosiciones(List<Posicion> entradasPosiciones) {
        EntradasHolder.entradasPosiciones = entradasPosiciones;
    }
}

