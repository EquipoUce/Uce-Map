package com.example.ucemap.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucemap.R;
import com.example.ucemap.repository.modelo.Posicion;
import com.example.ucemap.service.EntradaServiceImpl;
import com.example.ucemap.service.IEntradaService;
import com.example.ucemap.service.informacionFactory.IInformacionFactory;
import com.example.ucemap.repository.modelo.InformacionGeneral;
import com.example.ucemap.service.informacionFactory.InformacionFactory;
import com.example.ucemap.service.informacionSingleton.EntradasHolder;
import com.example.ucemap.service.informacionSingleton.InformacionHolder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button botonGenerarCamino;
    private Button botonDetalles;
    private Button botonInformacion;
    private Button botonCamino2;
    private Intent intent;
    private InformacionGeneral informacion;
    private Posicion posicion;
    //Variables para implementar el mapa.
    GoogleMap mapa;
    private Marker destino, destino1, origen, origen1;
    private LatLng destinoLL, origenLL;
    private List<LatLng> ruta;
    private List<LatLng> rutaTeatro;
    private String nombreRuta;
    public static final String DIRECTORIO_JASSON = "rutas.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        botonGenerarCamino = findViewById(R.id.bottonCamino);
        botonDetalles = findViewById(R.id.bottonDetalles);
        botonInformacion = findViewById(R.id.bottonInformacion);
        botonCamino2 = findViewById(R.id.bottonCamino2);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.idMapaGeneral);
        mapFragment.getMapAsync(this);

        //Actualizar titulo de Layout dependiendo de la entidad escogida
        TextView textTiruloMapa = findViewById(R.id.textTituloMapa);
        textTiruloMapa.setText(InformacionHolder.getNombreEntidadAsociada());

        //Uso de la bandera
        if (!InformacionHolder.getInformacionInicializada()) {
            try {
                //Aplicacion del Factory para crear la informacion
                IInformacionFactory iInformacionFactory = InformacionFactory.contruirInformacion(InformacionHolder.getTipoEntidadAsociada());
                informacion = iInformacionFactory.generarInformacion(this, InformacionHolder.getTipoAtributoAsociado(), InformacionHolder.getNombreEntidadAsociada());
                InformacionHolder.setInformacion(informacion);
                InformacionHolder.setInformacionInicializada(true);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.nombreRuta = InformacionHolder.getNombreEntidadAsociada();


        //-------------------------------------------------------------------------------------------
        posicion = InformacionHolder.getInformacion().getPosicion(); //<--- Posicion
        //-------------------------------------------------------------------------------------------
        botonGenerarCamino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapa.clear();
                try {
                    ruta = coordenadaRuta(leerJson(MapaActivity.this, DIRECTORIO_JASSON), "coordenadas");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                drawRoute(ruta);

                origenLL = ruta.get(0);
                destinoLL = ruta.get(ruta.size() - 1);

                origen1 = mapa.addMarker(new MarkerOptions()
                        .title("ESTA AQUI")
                        .position(origenLL)
                );


                destino1 = mapa.addMarker(new MarkerOptions()
                        .title(nombreRuta)
                        .position(destinoLL)
                );
                destino1.showInfoWindow();

                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(destinoLL, 15));
            }
        });

        botonCamino2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapa.clear();
                try {
                    rutaTeatro = coordenadaRuta(leerJson(MapaActivity.this, DIRECTORIO_JASSON), "coordenadas2");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                drawRoute(rutaTeatro);

                origenLL = rutaTeatro.get(0);
                destinoLL = rutaTeatro.get(rutaTeatro.size() - 1);

                origen = mapa.addMarker(new MarkerOptions()
                        .title("ESTA AQUI")
                        .position(origenLL)
                );


                destino = mapa.addMarker(new MarkerOptions()
                        .title(nombreRuta + " - Teatro")
                        .position(destinoLL)
                );
               destino.showInfoWindow();

                mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(destinoLL,15));


            }
        });

        //-------------------------------------------------------------------------------------------
        //Posiciones de las Entradas
        if (!EntradasHolder.getInformacionInicializada()) {
            try {

                IEntradaService iEntradaService = new EntradaServiceImpl();
                List<Posicion> tmp = iEntradaService.buscarTodasLasEntradasPorIdentificador(this);
                EntradasHolder.setEntradasPosiciones(tmp);
                EntradasHolder.setInformacionInicializada(true);
                Log.d("Se cargo las posiciones", tmp.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //Me tocan mi parte del codigo y los saco del grupo
        //-------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------

        //-------------------------------------------------------------------------------------------

        //-------------------------------------------------------------------------------------------
        botonDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MapaActivity.this, ListaEntradasActivity.class);
                startActivity(intent);
                //finish();
            }
        });


        botonInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MapaActivity.this, InformacionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MapaActivity.this, ListaOpcionesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LatLng inicio = new LatLng(posicion.getLatitud(),posicion.getLongitud());
        destino1 = mapa.addMarker(new MarkerOptions()
                .title(posicion.getIdentificador())
                .position(inicio)
        );
       destino1.showInfoWindow();

        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(inicio, (float) 17.5F));
    }
    @NonNull
    private List<LatLng> coordenadaRuta(String json,String nombre) throws Exception {
        List<LatLng> coordenadas = new ArrayList<>();

        JSONObject jsonO = new JSONObject(json);
        JSONArray features = jsonO.getJSONArray(this.nombreRuta);
        if (features.length() > 0) {
            JSONArray vectorCoordenadas = features.getJSONObject(0)
                    .getJSONObject("camino")
                    .getJSONArray(nombre);

            for (int i = 0; i < vectorCoordenadas.length(); i++) {
                JSONArray coordenada = vectorCoordenadas.getJSONArray(i);
                double latitude = coordenada.getDouble(0);
                double longitude = coordenada.getDouble(1);
                coordenadas.add(new LatLng(latitude, longitude));
            }
        }

        return coordenadas;
    }

    //RUTAS
    private String leerJson(Context context, String nombre) throws IOException
    {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(context.getAssets().open(nombre), "UTF-8"));
        String content = "";
        String line;
        while ((line = reader.readLine()) != null)
        {
            content = content + line;
        }
        return content;
    }
    private void drawRoute(List<LatLng> routeCoordinates) {
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(routeCoordinates)
                .width(10)
                .color(Color.BLUE);
        Polyline polyline = mapa.addPolyline(polylineOptions);
    }
}