package com.example.ucemap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucemap.R;
import com.example.ucemap.repository.modelo.OpcionEscogida;
import com.example.ucemap.service.informacionSingleton.InformacionHolder;
import com.example.ucemap.service.listaOpcionesFactory.IListaOpcionesFactory;
import com.example.ucemap.service.listaOpcionesFactory.ListaOpcionesFactory;
import com.example.ucemap.ui.adapters.RecycleViewAdaptadorListaEntradas;
import com.example.ucemap.ui.adapters.RecycleViewAdaptadorListaOpcionesEscogidas;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class ListaEntradasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListaEntradas;
    private RecycleViewAdaptadorListaEntradas adaptadorEntradas;
    private List<String> listaOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_opciones);

        // Atualizar titulo de Layout dependiendo del documento escogido
        TextView tipoListaOpciones = findViewById(R.id.tipoListaOpciones);
        tipoListaOpciones.setText(MenuPrincipalActivity.tituloLayout);

        //Implemente Mapa Aqui
        //----------------------------------------------------------------------------


        //---------------------------------------------------------------------

        listaOpciones = InformacionHolder.getInformacion().getDetalleEntradas();
        //Cargamos el Recycle con el nombre de las entidades escogidas
        recyclerViewListaEntradas = (RecyclerView) findViewById(R.id.recycleOpciones);
        recyclerViewListaEntradas.setLayoutManager(new LinearLayoutManager(this));
        adaptadorEntradas = new RecycleViewAdaptadorListaEntradas(this, listaOpciones);
        recyclerViewListaEntradas.setAdapter(adaptadorEntradas);

        recyclerViewListaEntradas.setPadding(
                recyclerViewListaEntradas.getPaddingLeft(),
                recyclerViewListaEntradas.getPaddingTop(),
                recyclerViewListaEntradas.getPaddingRight(),
                MainActivity.alturaBarraNavegacion + getResources().getDimensionPixelSize(R.dimen.margen_inferior_default)
        );

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
}
