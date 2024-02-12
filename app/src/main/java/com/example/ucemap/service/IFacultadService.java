package com.example.ucemap.service;

import android.content.Context;

import com.example.ucemap.repository.modelo.Descripcion;
import com.example.ucemap.repository.modelo.Facultad;
import com.example.ucemap.repository.modelo.OpcionEscogida;
import com.example.ucemap.repository.modelo.Posicion;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface IFacultadService {

    public  Facultad buscarFacultadPorNombre(Context context, String atributoParaExtraer, String atributoComparacion) throws IOException, JSONException;
    public List<OpcionEscogida> buscarTodasLasFacultadPorNombre(Context context) throws JSONException, IOException;
    public List<Descripcion> crearDescripcionFacultad(Facultad facultad);
    public  Posicion generarPosicionFacultad(Facultad facultad);
}
