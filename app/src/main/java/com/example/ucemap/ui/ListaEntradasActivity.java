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
import com.example.ucemap.utilidades.FuncionesAdicionales;
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
    private LatLng posicionGeneral;
    private Marker marcadorGeneral;
    private String validar;
    private Integer validarEntero;

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
        validar = FuncionesAdicionales.extraerNumeroString(listaOpciones.get(0));
        validarEntero = Integer.parseInt(validar);

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

        posicionGeneral = new LatLng(entradasPosicion.get(validarEntero-1).getLatitud(),entradasPosicion.get(validarEntero-1).getLongitud());
        marcadorGeneral = mapa.addMarker(new MarkerOptions().title(entradasPosicion.get(validarEntero-1).getIdentificador())
                        .position(posicionGeneral)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_posicion_actual))
        );
        marcadorGeneral.setTag(0);
        marcadorGeneral.showInfoWindow();

        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionGeneral, (float) 15.5F));
    }
}