package com.example.ucemap.repository;

import android.content.Context;
import android.util.Log;

import com.example.ucemap.data.jason.DatosJason;
import com.example.ucemap.repository.modelo.Facultad;
import com.example.ucemap.repository.modelo.OpcionEscogida;
import com.example.ucemap.repository.modelo.Posicion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntradaRepositoryImpl implements IEntradaRepository{
    @Override
    public Posicion seleccionarEntradaPorIdentificador(Context context, String atributoParaExtraer) throws IOException, JSONException {
        String jsonFileContent = DatosJason.leerJson(context);
        JSONObject jsonObject = new JSONObject(jsonFileContent);
        JSONArray documentoArray = jsonObject.getJSONArray(DatosJason.DOCUMENTOS_INTERNOS[2]);
        for (int i = 0; i < documentoArray.length(); i++) {
            JSONObject Object = documentoArray.getJSONObject(i);
            String atributoExtraido = Object.getString(atributoParaExtraer);
            if (atributoExtraido.equals(Posicion.ENTRADAS_ATRIBUTOS_JASON[0])) {
                Posicion posicion = new Posicion();
                posicion.setIdentificador(Object.getString(Posicion.ENTRADAS_ATRIBUTOS_JASON[0]));
                posicion.setLatitud(Object.getDouble( Posicion.ENTRADAS_ATRIBUTOS_JASON[1]));
                posicion.setLongitud(Object.getDouble( Posicion.ENTRADAS_ATRIBUTOS_JASON[2]));
                Log.d("Objeto Facultad", posicion.toString());
                return posicion;
            }
        }
        Log.e("ERROR", "No se creo una Facultas");
        return null;
    }

    @Override
    public List<Posicion> seleccionarTodasLasEntradasPorIdentificador(Context context) throws JSONException, IOException {
        List<Posicion> posicionesEntrada = new ArrayList<>();
        String jsonFileContent = DatosJason.leerJson(context);
        JSONObject jsonObject = new JSONObject(jsonFileContent);
        JSONArray documentoArray = jsonObject.getJSONArray(DatosJason.DOCUMENTOS_INTERNOS[2]);
        for (int i = 0; i < documentoArray.length(); i++) {
            JSONObject Object = documentoArray.getJSONObject(i);
            Posicion posicion = new Posicion();
            posicion.setIdentificador(Object.getString(Posicion.ENTRADAS_ATRIBUTOS_JASON[0]));
            posicion.setLatitud(Object.getDouble( Posicion.ENTRADAS_ATRIBUTOS_JASON[1]));
            posicion.setLongitud(Object.getDouble( Posicion.ENTRADAS_ATRIBUTOS_JASON[2]));
            posicionesEntrada.add(posicion);
        }
        Log.e("ERROR", "No se creo una Facultas");
        return posicionesEntrada;

    }
}
