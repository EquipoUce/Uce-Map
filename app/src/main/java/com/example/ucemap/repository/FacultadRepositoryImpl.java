package com.example.ucemap.repository;

import android.content.Context;
import android.util.Log;

import com.example.ucemap.data.jason.DatosJason;
import com.example.ucemap.repository.modelo.Facultad;
import com.example.ucemap.repository.modelo.OpcionEscogida;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class FacultadRepositoryImpl implements IFacultadRepository {
    @Override
    public Facultad seleccionarFacultadPorNombre(Context context, String atributoParaExtraer, String atributoComparacion) throws IOException, JSONException {
        String jsonFileContent = DatosJason.leerJson(context);
        JSONObject jsonObject = new JSONObject(jsonFileContent);
        JSONArray documentoArray = jsonObject.getJSONArray(DatosJason.DOCUMENTOS_INTERNOS[0]);
        for (int i = 0; i < documentoArray.length(); i++) {
            JSONObject Object = documentoArray.getJSONObject(i);
            String atributoExtraido = Object.getString(atributoParaExtraer);
            if (atributoExtraido.equals(atributoComparacion)) {
                // Coincidencia encontrada, construir el objeto Edificio
                Facultad facultad = new Facultad();
                facultad.setNombre(Object.getString(Facultad.FACULTAD_ATRIBUTOS_JASON[0]));
                facultad.setServicios(DatosJason.extraerListaString(Object,Facultad.FACULTAD_ATRIBUTOS_JASON[1]));
                facultad.setImagenes(DatosJason.extraerListaString(Object,Facultad.FACULTAD_ATRIBUTOS_JASON[2]));
                facultad.setInformacion(Object.getString( Facultad.FACULTAD_ATRIBUTOS_JASON[3]));
                facultad.setDetallesEntradas(DatosJason.extraerListaString(Object,Facultad.FACULTAD_ATRIBUTOS_JASON[4]));
                facultad.setLatitud(Object.getDouble( Facultad.FACULTAD_ATRIBUTOS_JASON[5]));
                facultad.setLongitud(Object.getDouble( Facultad.FACULTAD_ATRIBUTOS_JASON[6]));
                Log.d("Objeto Facultad", facultad.toString());
                return facultad;
            }
        }
        Log.e("ERROR", "No se creo una Facultas");
        return null;
    }

    @Override
    public List<OpcionEscogida> seleccionarTodasLasFacultadPorNombre(Context context) throws JSONException, IOException {
        return DatosJason.extraerUnAtributo(context, DatosJason.DOCUMENTOS_INTERNOS[0], DatosJason.ATRIBUTO_GENERAL_NOMBRE);
    }




}
