package com.example.ucemap.repository;

import android.content.Context;

import com.example.ucemap.repository.modelo.Edificio;
import com.example.ucemap.repository.modelo.OpcionEscogida;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface IEdificioRepository {
    public Edificio seleccionarEdificioPorNombre(Context context, String atributoParaExtraer, String atributoComparacion) throws IOException, JSONException;

    public List<OpcionEscogida> seleccionarTodasLosEdificiosPorNombre(Context context) throws IOException, JSONException;


}
