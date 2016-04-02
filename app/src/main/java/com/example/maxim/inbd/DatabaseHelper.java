package com.example.maxim.inbd;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Maxim on 25.02.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "EngDB"; // Имя базы данных
    private static final int DB_VERSION = 1; // Версия базы данных
    public static final String DATABASE_TABLE = "words";
    public static final String EnglWord = "EnglWord";
    public static final String COLUMN_ID = "_id";
    public static final String TRANSLATE = "TRANSLATE";
    private static final String DATABASE_CREATE_SCRIPT = "create table " + DATABASE_TABLE + " (" + "_id integer primary key autoincrement,"  + EnglWord + " text not null, " + TRANSLATE +" text not null);";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
