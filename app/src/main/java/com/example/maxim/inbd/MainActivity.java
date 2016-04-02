package com.example.maxim.inbd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void add(View view) {
        Intent intent=new Intent(this,AddWordActivity.class);
        startActivity(intent);
    }

    public void start(View view) {
        Intent intent=new Intent(this,GameActivity.class);
        startActivity(intent);
    }

    public void AllWord(View view) {
        Intent intent=new Intent(this,AllWordActivity.class);
        startActivity(intent);
    }

    public void proverka(View view) {
        Intent intent=new Intent(this,OnlineGameActivity.class);
        startActivity(intent);
    }
}
