package com.example.ucemap.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ucemap.R;
import com.example.ucemap.service.informacionSingleton.InformacionHolder;
import com.example.ucemap.ui.MapaActivity;
import com.example.ucemap.repository.modelo.OpcionEscogida;

import java.util.List;

public class RecycleViewAdaptadorListaOpcionesEscogidas extends RecyclerView.Adapter<RecycleViewAdaptadorListaOpcionesEscogidas.ViewHolder> {
    public List<OpcionEscogida> listaOpcioneModelos;
    private OnItemClickListener listener;
    private Intent intent;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView opcion;

        public ViewHolder(View itemView) {
            super(itemView);
            opcion = itemView.findViewById(R.id.textOpcion);
        }
    }

    public RecycleViewAdaptadorListaOpcionesEscogidas(Context context, List<OpcionEscogida> opciones) {
        this.context = context;
        this.listaOpcioneModelos = opciones;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opcion_escogida, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        String opcionEscogida = this.listaOpcioneModelos.get(position).getOpcion();
        holder.opcion.setText(opcionEscogida);

        // Añade el click a cada elemento visto
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(context, MapaActivity.class);
                InformacionHolder.setNombreEntidadAsociada(opcionEscogida);
                InformacionHolder.setInformacionInicializada(false); //bandera
                context.startActivity(intent);


            }

        });
    }

    @Override
    public int getItemCount() {
        return listaOpcioneModelos.size();
    }

}