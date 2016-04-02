package com.example.maxim.inbd;

import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.TextView;

public class RecordsActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Intent intent = getIntent();
        int countWord = intent.getIntExtra("countWord", 0);
        int countWordTrue = intent.getIntExtra("countWordTrue",0);
        textView=(TextView)findViewById(R.id.textView5);
        int falseWord=countWord-countWordTrue;
        int proc=falseWord*100/countWord;
        textView.setText("Слов потренировано "+countWord+", правильных ответов  "+countWordTrue+"\n  Процент неправильных ответов: "+proc);
    }
    @Override
    public void onBackPressed() {
        onNavigateUp();
    }
}
