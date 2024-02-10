package com.example.ucemap.service.informacionSingleton;

import com.example.ucemap.repository.modelo.Informacion;
import com.example.ucemap.repository.modelo.ListaOpciones;

import java.util.List;

public class InformacionHolder {
    private static InformacionHolder instanciaUnica;
    private static String tipoEntidadAsociada;
    private static String tipoAtributoAsociado;

    private static String nombreEntidadAsociada;
    private static Informacion informacion;
    private static boolean informacionInicializada = false;

    private static boolean banderalistaOpciones = false;

    private static List<ListaOpciones> listaOpcionesCreada;

    private InformacionHolder() {}
    public static InformacionHolder obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new InformacionHolder();
        }
        return instanciaUnica;
    }

    public static InformacionHolder getInstanciaUnica() {
        return instanciaUnica;
    }

    public static void setInstanciaUnica(InformacionHolder instanciaUnica) {
        InformacionHolder.instanciaUnica = instanciaUnica;
    }

    public static String getTipoEntidadAsociada() {
        return tipoEntidadAsociada;
    }

    public static void setTipoEntidadAsociada(String tipoEntidadAsociada) {
        InformacionHolder.tipoEntidadAsociada = tipoEntidadAsociada;
    }

    public static String getTipoAtributoAsociado() {
        return tipoAtributoAsociado;
    }

    public static void setTipoAtributoAsociado(String tipoAtributoAsociada) {
        InformacionHolder.tipoAtributoAsociado = tipoAtributoAsociada;
    }

    public static String getNombreEntidadAsociada() {
        return nombreEntidadAsociada;
    }

    public static void setNombreEntidadAsociada(String nombreEntidadAsociada) {
        InformacionHolder.nombreEntidadAsociada = nombreEntidadAsociada;
    }

    public static Informacion getInformacion() {
        return informacion;
    }

    public static void setInformacion(Informacion informacion) {
        InformacionHolder.informacion = informacion;
    }

    public static boolean getInformacionInicializada() {
        return informacionInicializada;
    }

    public static void setInformacionInicializada(boolean informacionInicializada) {
        InformacionHolder.informacionInicializada = informacionInicializada;
    }

    public static boolean getBanderalistaOpciones() {
        return banderalistaOpciones;
    }

    public static void setBanderalistaOpciones(boolean banderalistaOpciones) {
        InformacionHolder.banderalistaOpciones = banderalistaOpciones;
    }

    public static List<ListaOpciones> getListaOpcionesCreada() {
        return listaOpcionesCreada;
    }

    public static void setListaOpcionesCreada(List<ListaOpciones> listaOpcionesCreada) {
        InformacionHolder.listaOpcionesCreada = listaOpcionesCreada;
    }
}

