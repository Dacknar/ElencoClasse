package com.alte.dank.elencoclasse;

import android.content.ContentValues;
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

import java.util.ArrayList;

public class MaterieFragment extends Fragment {

    ArrayList<String> materie = new ArrayList<>();
    RecyclerAdapter adapter;
    DBHelper dbHelper;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_materie, container, false);
        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBHelper.DB_MATERIE, null,null,null,null,null,null);
        if(cursor.moveToFirst()){
           do{
                materie.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_MATERIA)));
            }while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new RecyclerAdapter(materie, getActivity().getApplicationContext(), sqLiteDatabase);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_materie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
    public void addMateria(String materia){
        adapter.addMateria(materia);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_MATERIA, materia);
        sqLiteDatabase.insert(DBHelper.DB_MATERIE, null, contentValues);
        Log.d("TTT", "Inserito valore " + materia + " in " + DBHelper.KEY_MATERIA);
    }
}
