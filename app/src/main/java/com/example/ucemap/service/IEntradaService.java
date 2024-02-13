package com.example.ucemap.service;

import android.content.Context;

import com.example.ucemap.repository.modelo.Posicion;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface IEntradaService {
    public Posicion buscarEntradaPorIdentificador(Context context, String identificador) throws IOException, JSONException;
    public List<Posicion> buscarTodasLasEntradasPorIdentificador(Context context) throws JSONException, IOException;
    
}
