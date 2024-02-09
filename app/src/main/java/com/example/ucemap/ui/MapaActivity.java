package com.example.ucemap.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ucemap.R;
import com.example.ucemap.data.DatosJason;
import com.example.ucemap.data.Rutas.HttpUtils;
import com.example.ucemap.repository.modelo.Posicion;
import com.example.ucemap.service.informacionFactory.IInformacionFactory;
import com.example.ucemap.repository.modelo.Informacion;
import com.example.ucemap.service.informacionFactory.InformacionFactory;
import com.example.ucemap.service.informacionSingleton.InformacionHolder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button botonGenerarCamino;
    private Button botonDetalles;
    private Intent intent;
    private Informacion informacion;
    private Posicion posicion;

    //Variables para implementar el mapa.
    GoogleMap mapa;
    private final int FINE_PERMISSION_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private LocationManager locationManager;
    Location currentLocation;
    private Marker destino, origen;

    public FusedLocationProviderClient fusedLocationProviderClient;
    public static boolean validar = false;

    private LatLng destinoLL, origenLL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        botonGenerarCamino = findViewById(R.id.bottonCamino);
        botonDetalles = findViewById(R.id.bottonDetalles);

        //iniciar las variables para el mapa
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapaActivity.this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.idMapaG);
        mapFragment.getMapAsync(this);
        // fin bloque inicializacion de variables


        //Aplicamos el objeto informacionHolder para extraer la informacion que no cambia
        InformacionHolder informacionHolder = InformacionHolder.obtenerInstancia();

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

        //-------------------------------------------------------------------------------------------
        posicion = InformacionHolder.getInformacion().getPosicion(); //<--- Posicion
        //-------------------------------------------------------------------------------------------
        botonGenerarCamino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapaActivity.this.validar =true;
                getLastLocation();
                new GetRouteTask().execute(origenLL,destinoLL);
                //finish();
            }
        });

        //-------------------------------------------------------------------------------------------
        botonDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MapaActivity.this, DetallesActivity.class);
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

    //METODOS PARA SOLICITAR PERMISOS DEL GPS
    private void getLastLocation() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if(checkLocationPermission()){
                Task<Location> task = fusedLocationProviderClient.getLastLocation();
                task.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null) {
                            currentLocation = location;
                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.idMapaG);
                            mapFragment.getMapAsync(MapaActivity.this);
                        }
                    }
                });
            }else{
                requestLocationPermission();
            }
        }else{
            Toast.makeText(this, "El GPS no está habilitado", Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(this,"Locacion nenegada, active los permisos" , Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if(validar){
            origenLL = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
            origen = mapa.addMarker(new MarkerOptions().title("POCISION ORIGINAL")
                            .position(origenLL)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_posicion_actual))

            );
            origen.setTag(0);
            //mapa.moveCamera(CameraUpdateFactory.newLatLng(universidad));
            //mapa.moveCamera(CameraUpdateFactory.newLatLng(estadio));
            //mapa.setOnMarkerClickListener(this);
        }
        destinoLL = new LatLng(posicion.getLatitud(),posicion.getLongitud());
        destino = mapa.addMarker(new MarkerOptions()
                .title(posicion.getNombre())
                .position(destinoLL)

        );
        destino.setTag(0);

        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(destinoLL, (float) 17.5F));
    }

    //CLASE PRIVADA PARA GENERAR LAS RUTAS
    private class GetRouteTask extends AsyncTask<LatLng, Void, List<LatLng>> {

        @Override
        protected List<LatLng> doInBackground(LatLng... params) {
            LatLng origin = params[0];
            LatLng destination = params[1];

            try {
                String apiKey = "5b3ce3597851110001cf62489857f6ee5a794c3988ae9eed0073a98d";  // Reemplaza con tu clave de API de OpenRouteService
                String url ="https://api.openrouteservice.org/v2/directions/foot-walking?api_key="+ //"https://api.openrouteservice.org/v2/directions/driving-car?api_key=" en autp
                        apiKey +
                        "&start=" + origin.longitude + "," + origin.latitude +
                        "&end=" + destination.longitude + "," + destination.latitude;

                String response = HttpUtils.sendHttpGetRequest(url);

                // Procesar la respuesta JSON y extraer las coordenadas de la ruta
                return parseRouteCoordinates(response);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<LatLng> routeCoordinates) {
            if (routeCoordinates != null) {
                // Mostrar la ruta en el mapa
                drawRoute(routeCoordinates);
            }
        }

        private List<LatLng> parseRouteCoordinates(String json) throws Exception {
            List<LatLng> coordinates = new ArrayList<>();

            JSONObject jsonResponse = new JSONObject(json);
            JSONArray features = jsonResponse.getJSONArray("features");
            if (features.length() > 0) {
                JSONArray coordinatesArray = features.getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONArray("coordinates");

                for (int i = 0; i < coordinatesArray.length(); i++) {
                    JSONArray coordinate = coordinatesArray.getJSONArray(i);
                    double latitude = coordinate.getDouble(1);
                    double longitude = coordinate.getDouble(0);
                    coordinates.add(new LatLng(latitude, longitude));
                }
            }

            return coordinates;
        }

        private void drawRoute(List<LatLng> routeCoordinates) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(routeCoordinates)
                    .width(9)
                    .color(Color.GREEN);

            Polyline polyline = mapa.addPolyline(polylineOptions);
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(routeCoordinates.get(0), 12));
        }
    }


}