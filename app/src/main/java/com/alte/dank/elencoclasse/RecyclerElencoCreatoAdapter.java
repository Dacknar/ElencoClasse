package com.alte.dank.elencoclasse;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerElencoCreatoAdapter extends RecyclerView.Adapter<RecyclerElencoCreatoAdapter.ViewHolder>{

    ArrayList<Integer> ordine;
    ArrayList<String> studenti;
    public RecyclerElencoCreatoAdapter(ArrayList<Integer> ordine, ArrayList<String> studenti){
        this.ordine = ordine;
        this.studenti = studenti;
    }
    public void generatedElenco(){
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_elenco_creato_layout, parent, false);

        ViewHolder holder = new ViewHolder(view);
        Log.d("TTT", "onCreate: FUNZIA CREATE HOLDER");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerElencoCreatoAdapter.ViewHolder holder, int position) {
        holder.txtNomeStudente.setText(studenti.get(position));
        holder.txtNumeroOrdine.setText(String.valueOf(ordine.get(position)));
        Log.d("TTT", "onCreate: FUNZIA BIND VIEW");
    }

    @Override
    public int getItemCount() {
        return ordine.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNomeStudente;
        TextView txtNumeroOrdine;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNomeStudente = (TextView) itemView.findViewById(R.id.txtNomeStd);
            txtNumeroOrdine = (TextView) itemView.findViewById(R.id.txtNumero);
        }
    }
}
