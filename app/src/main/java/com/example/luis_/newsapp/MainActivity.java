package com.example.luis_.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonPublico;
    Button buttonObservador;
    Button buttonRecord;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPublico=(Button)findViewById(R.id.buttonPublico);
        buttonObservador=(Button)findViewById(R.id.buttonObservador);
        buttonRecord=(Button)findViewById(R.id.buttonRecord);
        buttonPublico.setOnClickListener(this);
        buttonRecord.setOnClickListener(this);
        buttonObservador.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String url;
        String title;
        switch (view.getId()) {
            case R.id.buttonPublico:
                url = "https://feeds.feedburner.com/PublicoRSS";
                title = "Público";
                Intent intent = new Intent(this, NewsListActivity.class);
                intent.putExtra("extra_title", title);
                intent.putExtra("extra_url", url);
                startActivity(intent);
                break;
            case R.id.buttonObservador:
                break;
            case R.id.buttonRecord:
                break;
        }
    }


}
