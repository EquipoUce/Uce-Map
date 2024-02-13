package com.example.ucemap.repository;

import android.content.Context;

import com.example.ucemap.repository.modelo.Facultad;
import com.example.ucemap.repository.modelo.OpcionEscogida;
import com.example.ucemap.repository.modelo.Posicion;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface IEntradaRepository {
    public Posicion seleccionarEntradaPorIdentificador(Context context, String identificador) throws IOException, JSONException;
    public List<Posicion> seleccionarTodasLasEntradasPorIdentificador(Context context) throws JSONException, IOException;
}
