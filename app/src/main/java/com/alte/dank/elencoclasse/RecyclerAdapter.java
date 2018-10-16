package com.alte.dank.elencoclasse;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    ArrayList<String> materie;
    Context mContext;
    SQLiteDatabase sqLiteDatabase;
    public RecyclerAdapter(ArrayList<String> materie, Context mContext,SQLiteDatabase sqLiteDatabase) {
        this.materie = materie;
        this.mContext = mContext;
        this.sqLiteDatabase = sqLiteDatabase;
    }
    public void addMateria(String materia){
        materie.add(materia);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.materie_layout, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final int currentpos = position;
        holder.materia.setText(String.valueOf(materie.get(position)));
        holder.btnRimuovi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, materie.get(position) + " è stata rimossa", Toast.LENGTH_SHORT).show();
                sqLiteDatabase.delete(DBHelper.DB_MATERIE, DBHelper.KEY_MATERIA + "= ?",new String[] {materie.get(position)}); //DElete dal DB
                sqLiteDatabase.delete(DBHelper.DB_ORDINE, DBHelper.KEY_MATERIA + "= ?",new String[] {materie.get(position)}); //DElete dal DB

                materie.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, materie.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return materie.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView materia;
        Button btnRimuovi;

        public ViewHolder(View itemView) {
            super(itemView);
            materia = (TextView) itemView.findViewById(R.id.txtMateria);
            btnRimuovi = (Button) itemView.findViewById(R.id.btnRimuovi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, ElencoFatto.class);
                    i.putExtra("Materia", materie.get(getAdapterPosition()));
                    mContext.startActivity(i);
                }
            });
        }
    }
}