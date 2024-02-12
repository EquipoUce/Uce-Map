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
import com.example.ucemap.repository.modelo.OpcionEscogida;
import com.example.ucemap.service.informacionSingleton.InformacionHolder;
import com.example.ucemap.ui.MapaActivity;

import java.util.List;

public class RecycleViewAdaptadorListaEntradas extends RecyclerView.Adapter<RecycleViewAdaptadorListaEntradas.ViewHolder> {
    public List<String> listaOpcioneModelos;
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

    public RecycleViewAdaptadorListaEntradas(Context context, List<String> opciones) {
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
        //Texto que se presenta
        String opcionEscogida = this.listaOpcioneModelos.get(position);
        holder.opcion.setText(opcionEscogida);

        // Añade el click a cada elemento visto
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Implemente Audio Aqui
                //----------------------------------------------------------------------------


                //---------------------------------------------------------------------

            }

        });
    }

    @Override
    public int getItemCount() {
        return listaOpcioneModelos.size();
    }

}