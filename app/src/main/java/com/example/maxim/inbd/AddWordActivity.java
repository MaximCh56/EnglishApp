package com.example.maxim.inbd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddWordActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    EditText editText;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        editText=(EditText)findViewById(R.id.editText);
        editText2=(EditText)findViewById(R.id.editText2);
        databaseHelper = new DatabaseHelper(this);
        db=databaseHelper.getReadableDatabase();
        cursor=db.query("words",new String[] {"EnglWord", "TRANSLATE"},null, null, null, null, null);
        if (cursor!=null){
            //Toast.makeText(this,"Count "+cursor.getCount(),Toast.LENGTH_LONG).show();
        }
    }

    public void AddWord(View view) {
        String word=editText.getText().toString();
        String translate=editText2.getText().toString();
        insertWord(db,word,translate);
    }
    private static void insertWord(SQLiteDatabase db, String word, String translate) {
        ContentValues contentValues=new ContentValues();
        contentValues.put("EnglWord",word);
        contentValues.put("TRANSLATE",translate);
        db.insert("words",null,contentValues);
    }

}
