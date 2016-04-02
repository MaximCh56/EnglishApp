package com.example.maxim.inbd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Maxim on 12.03.2016.
 */
public class ExampleGame implements Subject {
    private ArrayList<Observer> observers;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    int count;
    int countWord;
    int countWordTrue;
    Cursor cursor;
    Random random;
    String word;
    String trueTranslate;
    String falseTranslate;
    String tableName;
    String uslovie1;
    String uslovie2;
    Context context;

    ExampleGame(String tableName,String uslovie1,String uslovie2,Context context) {

        observers = new ArrayList();
        this.tableName=tableName;
        this.uslovie1=uslovie1;
        this.uslovie2=uslovie2;
        this.context=context;
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getReadableDatabase();
        cursor=db.query(tableName, new String[]{uslovie1, uslovie2}, null, null, null, null, null);
        count=cursor.getCount();
    }

    public void game() {
        random=new Random();
        countWord++;
        int numRandom = (random.nextInt(count) + 1);
        Log.d("LOG", "numRandom " + numRandom);
        int falseRandom = recursRandom(numRandom);
        Log.d("LOG", "falseRandom " + falseRandom);
        String selection = "_id = ?";
        String[] selectionArgs = new String[]{String.valueOf(numRandom)};
        cursor = db.query(tableName, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            word = cursor.getString(cursor.getColumnIndex(databaseHelper.EnglWord));
            trueTranslate = cursor.getString(cursor.getColumnIndex(databaseHelper.TRANSLATE));
        }
        Log.d("LOG", "word " + word);
        String[] selectionArgs2 = new String[]{String.valueOf(falseRandom)};
        Cursor c = db.query(tableName, null, selection, selectionArgs2, null, null, null);
        if (c.moveToFirst()) {
            falseTranslate = c.getString(c.getColumnIndex(databaseHelper.TRANSLATE));
        }
        notifyObservers();
    }

    private int recursRandom(int n) {
        int r = n;
        int z = (random.nextInt(count) + 1);
        if (r == z || z == 0) {
            z = recursRandom(r);
        }
        return z;
    }
    public void proverka(String s){
        if (s.equals(trueTranslate)){
            countWordTrue++;
        }
        game();
    }


    @Override
    public void registerObserver(Observer o) {
        observers.add(o);

    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }


    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.update(word, trueTranslate, falseTranslate);


        }
    }
    public int getCount() {
        return count;
    }
}
