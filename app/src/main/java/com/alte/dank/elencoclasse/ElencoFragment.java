package com.alte.dank.elencoclasse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ElencoFragment extends Fragment {
    ArrayList<String> studenti = new ArrayList<>();
    RecycleElencoAdapter adapter;
    RecyclerView recyclerView;
    Button addStudente;
    EditText edtStudente;
    DBHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_elenco, container, false);

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        adapter = new RecycleElencoAdapter(studenti, sqLiteDatabase);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_elenco);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        Cursor cursor = sqLiteDatabase.query(DBHelper.DB_STUDENTI, null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                studenti.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)));
            }while (cursor.moveToNext());
        }
        cursor.close();

        edtStudente = (EditText) view.findViewById(R.id.edtStudente);

        addStudente = (Button) view.findViewById(R.id.btn_elenco_add_studente);
        addStudente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtStudente.getText().toString().isEmpty()){
                    adapter.addStudente(edtStudente.getText().toString());
                    SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.KEY_NAME, edtStudente.getText().toString());
                    sqLiteDatabase.insert(DBHelper.DB_STUDENTI, null, contentValues);
                    edtStudente.setText("");
                    edtStudente.clearFocus();
                }
            }
        });
        return view;
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
