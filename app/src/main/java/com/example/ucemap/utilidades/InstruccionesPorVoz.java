package com.example.ucemap.utilidades;


import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class InstruccionesPorVoz implements TextToSpeech.OnInitListener {
    public TextToSpeech texto;

    public InstruccionesPorVoz(Context context){
        texto = new TextToSpeech(context,this);
    }
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int respuesta = texto.setLanguage(Locale.getDefault());
            if(respuesta == TextToSpeech.LANG_MISSING_DATA || respuesta == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TextToSpeech","Error al inicializar TextToSpeech");
            }

        }
    }

    public void speak(String text){
        if(texto != null){
            texto.speak(text,TextToSpeech.QUEUE_FLUSH,null,"Directions");
        }
    }
    public void stop(){
        if(texto != null){
            texto.stop();
        }
    }
    public void shutdown(){
        if(texto != null ){
            texto.shutdown();
        }
    }
}