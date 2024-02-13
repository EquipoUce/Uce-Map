package com.example.ucemap.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucemap.R;
import com.example.ucemap.repository.modelo.OpcionEscogida;
import com.example.ucemap.repository.modelo.Posicion;
import com.example.ucemap.service.EntradaServiceImpl;
import com.example.ucemap.service.IEntradaService;
import com.example.ucemap.service.informacionSingleton.EntradasHolder;
import com.example.ucemap.service.informacionSingleton.InformacionHolder;
import com.example.ucemap.service.listaOpcionesFactory.IListaOpcionesFactory;
import com.example.ucemap.service.listaOpcionesFactory.ListaOpcionesFactory;
import com.example.ucemap.ui.adapters.RecycleViewAdaptadorListaEntradas;
import com.example.ucemap.ui.adapters.RecycleViewAdaptadorListaOpcionesEscogidas;
import com.example.ucemap.utilidades.InstruccionesPorVoz;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class ListaEntradasActivity extends AppCompatActivity implements OnMapReadyCallback {

    private RecyclerView recyclerViewListaEntradas;
    private RecycleViewAdaptadorListaEntradas adaptadorEntradas;
    private List<String> listaOpciones;

    GoogleMap mapa;
    private List<Posicion> entradasPosicion;
    private LatLng p1,p2,p3,p4,p5,p6,p7,p8,po;
    private Marker m1,m2,m3,m4,m5,m6,m7,m8;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_entrada);

        //Implemente Mapa Aqui
        //----------------------------------------------------------------------------
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.idMapaEntradas);
        mapFragment.getMapAsync(this);

        //---------------------------------------------------------------------

        listaOpciones = InformacionHolder.getInformacion().getDetalleEntradas();
        //Cargamos el Recycle con el nombre de las entidades escogidas
        recyclerViewListaEntradas = (RecyclerView) findViewById(R.id.recycleEntradas);
        recyclerViewListaEntradas.setLayoutManager(new LinearLayoutManager(this));
        adaptadorEntradas = new RecycleViewAdaptadorListaEntradas(this, listaOpciones);

        recyclerViewListaEntradas.setAdapter(adaptadorEntradas);


        recyclerViewListaEntradas.setPadding(
                recyclerViewListaEntradas.getPaddingLeft(),
                recyclerViewListaEntradas.getPaddingTop(),
                recyclerViewListaEntradas.getPaddingRight(),
                MainActivity.alturaBarraNavegacion + getResources().getDimensionPixelSize(R.dimen.margen_inferior_default)
        );

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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(this, MapaActivity.class);
        //Para limpiar e evitar regresar a esta actividad
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        p1 = new LatLng(entradasPosicion.get(0).getLatitud(),entradasPosicion.get(0).getLongitud());
        p2 = new LatLng(entradasPosicion.get(1).getLatitud(),entradasPosicion.get(1).getLongitud());
        p3 = new LatLng(entradasPosicion.get(2).getLatitud(),entradasPosicion.get(2).getLongitud());
        p4 = new LatLng(entradasPosicion.get(3).getLatitud(),entradasPosicion.get(3).getLongitud());
        p5 = new LatLng(entradasPosicion.get(4).getLatitud(),entradasPosicion.get(4).getLongitud());
        p6 = new LatLng(entradasPosicion.get(5).getLatitud(),entradasPosicion.get(5).getLongitud());
        p7 = new LatLng(entradasPosicion.get(6).getLatitud(),entradasPosicion.get(6).getLongitud());
        p8 = new LatLng(entradasPosicion.get(7).getLatitud(),entradasPosicion.get(7).getLongitud());
        po = new LatLng(-0.199490,-78.505993);

        m1 = mapa.addMarker(new MarkerOptions().title("ENTRADA 1")
                            .position(p1)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_posicion_actual))
            );
        m1.setTag(0);
        m2 = mapa.addMarker(new MarkerOptions().title("ENTRADA 2")
                        .position(p2)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_posicion_actual))
        );
        m2.setTag(0);
        m3 = mapa.addMarker(new MarkerOptions().title("ENTRADA 3")
                        .position(p3)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_posicion_actual))
        );
        m3.setTag(0);
        m4 = mapa.addMarker(new MarkerOptions().title("ENTRADA 4")
                        .position(p4)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_posicion_actual))
        );
        m4.setTag(0);
        m5 = mapa.addMarker(new MarkerOptions().title("ENTRADA 5")
                        .position(p5)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_posicion_actual))
        );
        m5.setTag(0);
        m6 = mapa.addMarker(new MarkerOptions().title("ENTRADA 6")
                        .position(p6)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_posicion_actual))
        );
        m6.setTag(0);
        m7 = mapa.addMarker(new MarkerOptions().title("ENTRADA 7")
                        .position(p7)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_posicion_actual))
        );
        m7.setTag(0);
        m8 = mapa.addMarker(new MarkerOptions().title("ENTRADA 8")
                        .position(p8)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_posicion_actual))
        );
        m8.setTag(0);

        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(po, (float) 15.5F));
    }
}
