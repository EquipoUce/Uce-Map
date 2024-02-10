package com.example.ucemap.service.informacionSingleton;

import com.example.ucemap.repository.modelo.ListaOpciones;

import java.util.List;

public class ListaOpcionesSingleton {

    private static ListaOpcionesSingleton instance;
    private List<ListaOpciones> listaOpciones;

    private ListaOpcionesSingleton() {
        // Constructor privado para evitar instanciación directa.
    }

    public static synchronized ListaOpcionesSingleton getInstance() {
        if (instance == null) {
            instance = new ListaOpcionesSingleton();
        }
        return instance;
    }

    public List<ListaOpciones> getListaOpciones() {
        return listaOpciones;
    }

    public void setListaOpciones(List<ListaOpciones> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }
}

