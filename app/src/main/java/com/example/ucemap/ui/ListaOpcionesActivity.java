package com.example.ucemap.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ucemap.R;
import com.example.ucemap.repository.modelo.ListaOpciones;
import com.example.ucemap.service.informacionSingleton.InformacionHolder;
import com.example.ucemap.service.listaOpcionesFactory.IListaOpcionesFactory;
import com.example.ucemap.service.listaOpcionesFactory.ListaOpcionesFactory;
import com.example.ucemap.ui.adapters.RecycleViewAdaptadorListaOpciones;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class ListaOpcionesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListaOpciones;
    private RecycleViewAdaptadorListaOpciones adaptadorOpciones;
    private List<ListaOpciones> listaOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_opciones);

        // Atualizar titulo de Layout dependiendo del documento escogido
        TextView tipoListaOpciones = findViewById(R.id.tipoListaOpciones);
        tipoListaOpciones.setText(MenuPrincipalActivity.tituloLayout);

        if (!InformacionHolder.getBanderalistaOpciones()) {
            //Creamos la lista de Opciones
            try {
                IListaOpcionesFactory iListaOpcionesFactory = ListaOpcionesFactory.generarListaOpciones(InformacionHolder.getTipoEntidadAsociada());
                listaOpciones = iListaOpcionesFactory.crearListaOpciones(this);
                InformacionHolder.setListaOpcionesCreada(listaOpciones);
                InformacionHolder.setBanderalistaOpciones(true);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        listaOpciones = InformacionHolder.getListaOpcionesCreada();

        //Cargamos el Recycle con el nombre de las entidades escogidas
        recyclerViewListaOpciones = (RecyclerView) findViewById(R.id.recycleOpciones);
        recyclerViewListaOpciones.setLayoutManager(new LinearLayoutManager(this));
        adaptadorOpciones = new RecycleViewAdaptadorListaOpciones(this, listaOpciones);
        recyclerViewListaOpciones.setAdapter(adaptadorOpciones);

        recyclerViewListaOpciones.setPadding(
                recyclerViewListaOpciones.getPaddingLeft(),
                recyclerViewListaOpciones.getPaddingTop(),
                recyclerViewListaOpciones.getPaddingRight(),
                MainActivity.alturaBarraNavegacion + getResources().getDimensionPixelSize(R.dimen.margen_inferior_default)
        );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        //Para limpiar e evitar regresar a esta actividad
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
