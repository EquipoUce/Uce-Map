package com.example.ucemap.repository;

import android.content.Context;
import android.util.Log;

import com.example.ucemap.data.jason.DatosJason;
import com.example.ucemap.repository.modelo.Edificio;
import com.example.ucemap.repository.modelo.OpcionEscogida;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class EdificioRepositoryImpl implements IEdificioRepository {
    @Override
    public Edificio seleccionarEdificioPorNombre(Context context, String atributoParaExtraer, String atributoComparacion) throws IOException, JSONException {
        String jsonFileContent = DatosJason.leerJson(context);
        JSONObject jsonObject = new JSONObject(jsonFileContent);
        JSONArray documentoArray = jsonObject.getJSONArray(DatosJason.DOCUMENTOS_INTERNOS[1]);
        for (int i = 0; i < documentoArray.length(); i++) {
            JSONObject Object = documentoArray.getJSONObject(i);
            String atributoExtraido = Object.getString(atributoParaExtraer);
            if (atributoExtraido.equals(atributoComparacion)) {
                // Coincidencia encontrada, construir el objeto Edificio
                Edificio edificio = new Edificio();
                edificio.setNombre(Object.getString(Edificio.EDIFICIO_ATRIBUTOS_JASON[0]));
                edificio.setServicios(DatosJason.extraerListaString(Object,Edificio.EDIFICIO_ATRIBUTOS_JASON[1]));
                edificio.setImagenes(DatosJason.extraerListaString(Object,Edificio.EDIFICIO_ATRIBUTOS_JASON[2]));
                edificio.setDetallesEntradas(DatosJason.extraerListaString(Object,Edificio.EDIFICIO_ATRIBUTOS_JASON[3]));
                edificio.setInformacion(Object.getString( Edificio.EDIFICIO_ATRIBUTOS_JASON[4]));
                edificio.setLatitud(Object.getDouble( Edificio.EDIFICIO_ATRIBUTOS_JASON[5]));
                edificio.setLongitud(Object.getDouble( Edificio.EDIFICIO_ATRIBUTOS_JASON[6]));
                Log.d("Objeto Edificio", edificio.toString());
                return edificio;
            }
        }
        Log.e("ERROR", "No se creo un Edificio");
        return null;
    }

    @Override
    public List<OpcionEscogida> seleccionarTodasLosEdificiosPorNombre(Context context) throws IOException, JSONException {
        return DatosJason.extraerUnAtributo(context, DatosJason.DOCUMENTOS_INTERNOS[1], DatosJason.ATRIBUTO_GENERAL_NOMBRE);


    }

}
