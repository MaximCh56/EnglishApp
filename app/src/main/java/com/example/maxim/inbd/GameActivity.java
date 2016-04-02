package com.example.maxim.inbd;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity implements Observer {
    int count;
    TextView textView;
    Button Button1Word;
    Button Button2Word;
    Button ButtonStopGame;
    Random random;
    int buttonRandom;
    ProgressBar progressBar;
    MyTask myTask;
    ExampleGame exampleGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        random=new Random();
        exampleGame=new ExampleGame(DatabaseHelper.DATABASE_TABLE,DatabaseHelper.EnglWord,DatabaseHelper.TRANSLATE,this);
        count=exampleGame.getCount();
        textView=(TextView)findViewById(R.id.textView);
        Button1Word=(Button)findViewById(R.id.Button1Word);
        Button2Word=(Button)findViewById(R.id.Button2Word);
        ButtonStopGame=(Button)findViewById(R.id.ButtonStopGame);
        if (count<=4){
            textView.setText("Недостаточно слов, нужно минимум 5");
            Button1Word.setVisibility(View.INVISIBLE);
            Button2Word.setVisibility(View.INVISIBLE);
            ButtonStopGame.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
        else {
            exampleGame.registerObserver(this);
            exampleGame.game();
            myTask=new MyTask();
            myTask.execute();
        }
    }


    public void stopGame(View view) {
        Intent intent = new Intent(this, RecordsActivity.class);
        intent.putExtra("countWord", exampleGame.countWord);
        intent.putExtra("countWordTrue", exampleGame.countWordTrue);
        startActivity(intent);

    }


    public void onevariant(View view) {
        exampleGame.proverka(Button1Word.getText().toString());
        myTask.setCountTask(10);
    }

    public void twovariant(View view) {
        exampleGame.proverka(Button1Word.getText().toString());
        myTask.setCountTask(10);

    }

    @Override
    public void update(String quest, String trueAnswer, String falseAnswer) {
        textView.setText(quest);
        buttonRandom=random.nextInt(2);
        if(buttonRandom==0){
            Button1Word.setText(trueAnswer);
            Button2Word.setText(falseAnswer);
        }else {
            Button1Word.setText(falseAnswer);
            Button2Word.setText(trueAnswer);
        }

    }

    public class MyTask extends AsyncTask<Void,Integer,Void>{
        public void setCountTask(int countTask) {
            this.countTask = countTask;
        }

        int countTask;

        @Override
        protected void onPreExecute() {
            progressBar.setMax(10);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            if (countTask==-1){
                exampleGame.game();
                countTask=10;
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (countTask=10; countTask >= -2; countTask--) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    publishProgress(countTask);

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
