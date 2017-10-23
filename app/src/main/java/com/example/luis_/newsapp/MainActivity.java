package com.example.luis_.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "newsapp";

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
        Intent intent = new Intent(this, NewsListActivity.class);
        switch (view.getId()) {
            case R.id.buttonPublico:
                url = "https://feeds.feedburner.com/PublicoRSS";
                title = "Público";
                intent.putExtra(NewsListActivity.EXTRA_TITLE, title);
                intent.putExtra(NewsListActivity.EXTRA_URL, url);
                break;
            case R.id.buttonObservador:
                break;
            case R.id.buttonRecord:
                url = "http://www.record.pt/rss.aspx";
                title = "Record";
                intent.putExtra(NewsListActivity.EXTRA_TITLE, title);
                intent.putExtra(NewsListActivity.EXTRA_URL, url);
                break;
        }

        startActivity(intent);
    }


}
