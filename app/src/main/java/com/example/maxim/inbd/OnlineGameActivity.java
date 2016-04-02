package com.example.maxim.inbd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class OnlineGameActivity extends AppCompatActivity implements Observer {
    Button OnlineGamebutton1;
    Button OnlineGamebutton2;
    Button OnlineGameStop;
    TextView OnlineGameTextView;
    int buttonRandom;
    Random random;
    JSONParser jsonParser;
    int countWord;
    int countWordTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);
        OnlineGamebutton1=(Button)findViewById(R.id.OnlineGamebutton1);
        OnlineGamebutton2=(Button)findViewById(R.id.OnlineGamebutton2);
        OnlineGameStop=(Button)findViewById(R.id.OnlineGameStop);
        OnlineGameTextView=(TextView)findViewById(R.id.OnlineGameTextView);
        random=new Random();
        jsonParser=new JSONParser();
        jsonParser.registerObserver(this);
        jsonParser.execute();
        countWord++;
    }

    @Override
    public void update(String quest, String trueAnswer, String falseAnswer) {
        OnlineGameTextView.setText(quest);
        buttonRandom=random.nextInt(2);
        if(buttonRandom==0){
            OnlineGamebutton1.setText(trueAnswer);
            OnlineGamebutton2.setText(falseAnswer);
        }else {
            OnlineGamebutton1.setText(falseAnswer);
            OnlineGamebutton2.setText(trueAnswer);
        }

    }

    public void OnlineGamebutton2(View view) {
        jsonParser=new JSONParser();
        jsonParser.registerObserver(this);
        jsonParser.execute();
        countWord++;
        if (buttonRandom==1){
            countWordTrue++;
        }
        //jsonParser.proverka(OnlineGamebutton2.getText().toString());

    }

    public void OnlineGamebutton1(View view) {
        jsonParser=new JSONParser();
        jsonParser.registerObserver(this);
        jsonParser.execute();
        countWord++;
        if (buttonRandom==0){
            countWordTrue++;
        }
        //jsonParser.proverka(OnlineGamebutton1.getText().toString());

    }
    public void OnlineGameStop(View view) {
        Intent intent = new Intent(this, RecordsActivity.class);
        intent.putExtra("countWord", countWord);
        intent.putExtra("countWordTrue", countWordTrue);
        startActivity(intent);

    }
}
