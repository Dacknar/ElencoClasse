package com.alte.dank.elencoclasse;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class ElencoFatto extends AppCompatActivity {

    DBHelper dbHelper;
    RecyclerView recyclerView;
    RecyclerElencoCreatoAdapter rv_adapter;
    SQLiteDatabase db;
    ArrayList<Integer> ordine = new ArrayList<Integer>();
    ArrayList<String> studenti = new ArrayList<String>();
    String materia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_fatto);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        recyclerView = (RecyclerView) findViewById(R.id.rv_elencoCreato);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rv_adapter = new RecyclerElencoCreatoAdapter(ordine, studenti);
        recyclerView.setAdapter(rv_adapter);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            materia = extras.getString("Materia");
        }
        setTitle(materia);
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.DB_ORDINE + " WHERE " + DBHelper.KEY_MATERIA + " LIKE '" + materia + "'", null);
        if(cursor.moveToFirst()) { //SE c'è l'ordine, facciamolo vedere
            int count = 0;
                do {
                    count++;
                    ordine.add(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_NUMERO)));
                } while (cursor.moveToNext());
                if(count != (int) DatabaseUtils.queryNumEntries(db, DBHelper.DB_STUDENTI)){ // nel caso si aggiunga uno studente dopo, si ricrea l`elenco
                    ordine.clear();
                    rv_adapter.generatedElenco();
                    db.delete(DBHelper.DB_ORDINE, DBHelper.KEY_MATERIA + "= ?",new String[] {materia}); //DElete dal DB
                    createOrdine(ordine);
                }
        }
        else{//se no, creamol'
            createOrdine(ordine);
        }
        cursor.close();
        Log.d("TTT", "onCreate: FUNZIA ALLA FINE");
        Cursor c = db.query(DBHelper.DB_STUDENTI, null,null,null,null,null,null);
        if(c.moveToFirst()){ //SE c'è l'ordine, facciamolo vedere
            do{
                studenti.add(c.getString(c.getColumnIndex(DBHelper.KEY_NAME)));
            }while (c.moveToNext());
        }
        c.close();
    }
    public void createOrdine(ArrayList<Integer> ordine){
        int max = (int) DatabaseUtils.queryNumEntries(db, DBHelper.DB_STUDENTI);
        int[] numbers = new int[max];
        Random rnd = new Random();
        int randNum;
        boolean j;
        for(int i = 0; i < max; i++){
            do{
                j = true;
                randNum = rnd.nextInt(max) + 1;
                for(int y = 0; y < max; y++){
                    if(numbers[y] == randNum){
                        j = false;
                        break;
                    }
                }
            }while(!j);
            numbers[i] = randNum;
            ordine.add(randNum);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.KEY_MATERIA, materia);
            contentValues.put(DBHelper.KEY_NUMERO, randNum);
            db.insert(DBHelper.DB_ORDINE, null, contentValues);
            Log.d("TTT", "Inserito ordine " + randNum + " in " + DBHelper.KEY_MATERIA);
        }
        rv_adapter.generatedElenco();
    }

}
