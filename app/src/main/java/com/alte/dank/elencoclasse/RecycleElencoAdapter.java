package com.alte.dank.elencoclasse;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RecycleElencoAdapter extends RecyclerView.Adapter<RecycleElencoAdapter.ViewHolder> {

    ArrayList<String> studenti;
    SQLiteDatabase sqLiteDatabase;
    public RecycleElencoAdapter(ArrayList<String> studenti, SQLiteDatabase sqLiteDatabase) {
        this.studenti = studenti;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_elenco_layout, parent,false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }
    public void addStudente(String studente){
        studenti.add(studente);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.nomeStudente.setText(studenti.get(position).toString());
            holder.btnRimuovi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sqLiteDatabase.delete(DBHelper.DB_STUDENTI, DBHelper.KEY_NAME + "= ?",new String[] {studenti.get(position)}); //DElete dal DB
                    studenti.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, studenti.size());
                }
            });
    }
    @Override
    public int getItemCount() {
        return studenti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nomeStudente;
        Button btnRimuovi;
        public ViewHolder(View itemView) {
            super(itemView);
            nomeStudente = (TextView) itemView.findViewById(R.id.txtNomeStudente);
            btnRimuovi = (Button) itemView.findViewById(R.id.btn_elenco_rimuovi);
        }
    }
}
