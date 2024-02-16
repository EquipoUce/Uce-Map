package com.example.ucemap.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

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
    private Intent intent;
    private InformacionGeneral informacion;
    private Posicion posicion;
    private List<Posicion> entradasPosicion;

    //Variables para implementar el mapa.
    GoogleMap mapa;
    private Marker destino, origen;

    private LatLng destinoLL, origenLL;

    private List<LatLng> ruta;
    private String nombreRuta;

    public static final String DIRECTORIO_JASSON= "rutas.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        botonGenerarCamino = findViewById(R.id.bottonCamino);
        botonDetalles = findViewById(R.id.bottonDetalles);
        botonInformacion = findViewById(R.id.bottonInformacion);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.idMapaGeneral);
        mapFragment.getMapAsync(this);

        //Aplicamos el objeto informacionHolder para extraer la informacion que no cambia
        InformacionHolder informacionHolder = InformacionHolder.obtenerInstancia();
        EntradasHolder entradasHolder = EntradasHolder.obtenerInstancia();

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
        try {
            ruta = this.coordenadaRuta(this.leerJson(this));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
        //-------------------------------------------------------------------------------------------
        posicion = InformacionHolder.getInformacion().getPosicion(); //<--- Posicion
        //-------------------------------------------------------------------------------------------
        botonGenerarCamino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MapaActivity.this, " "+InformacionHolder.getNombreEntidadAsociada(), Toast.LENGTH_SHORT).show();
                drawRoute(ruta);
                //finish();
            }
        });

        //-------------------------------------------------------------------------------------------
        //Posiciones de las Entradas
        if (!EntradasHolder.getInformacionInicializada()) {
            try {

                IEntradaService iEntradaService =  new EntradaServiceImpl();
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
        entradasPosicion = EntradasHolder.getEntradasPosiciones();
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

        origenLL = ruta.get(0);
        destinoLL = ruta.get(ruta.size()-1);


        origen = mapa.addMarker(new MarkerOptions()
                .title("ESTA AQUI")
                .position(origenLL)
        );
        origen.showInfoWindow();

        destino = mapa.addMarker(new MarkerOptions()
                .title(this.nombreRuta)
                .position(destinoLL)
        );
        //destino.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_navigate_next_black_24dp));
        destino.showInfoWindow();


        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(destinoLL, (float) 17.5F));

    }
    @NonNull
    private List<LatLng> coordenadaRuta(String json) throws Exception {
        List<LatLng> coordenadas = new ArrayList<>();

        JSONObject jsonO = new JSONObject(json);
        JSONArray features = jsonO.getJSONArray(this.nombreRuta);
        if (features.length() > 0) {
            JSONArray vectorCoordenadas = features.getJSONObject(0)
                    .getJSONArray("coordenadas");

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
    private String leerJson(Context context) throws IOException
    {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(context.getAssets().open(DIRECTORIO_JASSON), "UTF-8"));
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
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(routeCoordinates.get(0), 15));
    }
}