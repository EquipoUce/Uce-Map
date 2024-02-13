package com.example.ucemap.service;

import android.content.Context;

import com.example.ucemap.repository.EntradaRepositoryImpl;
import com.example.ucemap.repository.IEntradaRepository;
import com.example.ucemap.repository.modelo.Posicion;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class EntradaServiceImpl implements IEntradaService {
    private IEntradaRepository iEntradaRepository;

    @Override
    public Posicion buscarEntradaPorIdentificador(Context context, String identificador) throws IOException, JSONException {
        iEntradaRepository = new EntradaRepositoryImpl();
        return iEntradaRepository.seleccionarEntradaPorIdentificador(context,identificador);
    }

    @Override
    public List<Posicion> buscarTodasLasEntradasPorIdentificador(Context context) throws JSONException, IOException {
        iEntradaRepository = new EntradaRepositoryImpl();
        return iEntradaRepository.seleccionarTodasLasEntradasPorIdentificador(context);
    }
}
