package com.alte.dank.elencoclasse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "classe";
    public static final String DB_MATERIE = "materie";
    public static final String DB_STUDENTI = "studenti";
    public static final String DB_ORDINE = "ordine";

    public String DB_TABNAME;

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "nome";
    public static final String KEY_NUMERO = "numero";
    public static final String KEY_MATERIA = "materia";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DB_MATERIE + " (" +
                KEY_ID + " integer primary key," +
                KEY_MATERIA + " text)");
        sqLiteDatabase.execSQL("CREATE TABLE " + DBHelper.DB_STUDENTI + " (" +
                DBHelper.KEY_ID + " integer primary key," +
                DBHelper.KEY_NAME + " text)");
        sqLiteDatabase.execSQL("CREATE TABLE " + DBHelper.DB_ORDINE + " (" +
                DBHelper.KEY_ID + " integer primary key," +
                DBHelper.KEY_MATERIA + " text," +
                DBHelper.KEY_NUMERO + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + DB_MATERIE);
        sqLiteDatabase.execSQL("drop table if exists " + DB_STUDENTI);

        onCreate(sqLiteDatabase);
    }
}
