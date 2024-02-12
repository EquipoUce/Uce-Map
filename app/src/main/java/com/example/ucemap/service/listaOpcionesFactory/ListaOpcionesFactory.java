package com.example.ucemap.service.listaOpcionesFactory;

import android.util.Log;

public class ListaOpcionesFactory {

    public static IListaOpcionesFactory generarListaOpciones(String tipo){
        if ("facultades".equalsIgnoreCase(tipo)) {
            Log.d("Tipo", tipo);
            return new ListaFacultades();
        } else if ("edificios".equalsIgnoreCase(tipo)) {
            return  new ListaEdificios();

        }
        return null;

    };
}
