package com.example.maxim.inbd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AllWordActivity extends AppCompatActivity {
    ListView listView;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter userAdapter;
    private static final int CM_DELETE_ID = 1;
    TextView textView4;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_word);
        textView4 = (TextView) findViewById(R.id.textView4);
        search = (EditText) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();
        showAllword();
        textView4.setText("Всего слов: " + cursor.getCount());
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить");
    }

    private void showAllword() {
        cursor = db.rawQuery("select * from " + "words", null);
        String[] headers = new String[]{"EnglWord", "TRANSLATE"};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, cursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        listView.setAdapter(userAdapter);
        registerForContextMenu(listView);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            delRec(acmi.id);
            // обновляем курсор
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    public void delRec(long id) {
        db.delete(databaseHelper.DATABASE_TABLE, databaseHelper.COLUMN_ID + " = " + id, null);
        //db = databaseHelper.getReadableDatabase();
        showAllword();
    }

    @Override
    protected void onResume() {
        super.onResume();

        userAdapter.getFilter().filter(search.getText().toString());

        // установка слушателя изменения текста
        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            // при изменении текста выполняем фильтрацию
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                userAdapter.getFilter().filter(s.toString());
            }
        });

        // устанавливаем провайдер фильтрации
        userAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {

                if (constraint == null || constraint.length() == 0) {

                    return db.rawQuery("select * from " + DatabaseHelper.DATABASE_TABLE, null);
                } else {
                    return db.rawQuery("select * from " + DatabaseHelper.DATABASE_TABLE + " where " + DatabaseHelper.EnglWord + " like ?", new String[]{"%" + constraint.toString() + "%"});
                }
            }
        });

        listView.setAdapter(userAdapter);
    }
}
