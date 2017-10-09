package com.example.luis_.newsapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;


public class NewsListActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "extra_url";
    public static final String EXTRA_TITLE = "extra_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String urlString = getIntent().getStringExtra(EXTRA_URL);
        setTitle(title);
        final TextView textViewTeste = (TextView)findViewById(R.id.textViewTeste);
        //textViewTeste.setText(url);

        HttpFetchNews httpFetchNews = new HttpFetchNews();
        httpFetchNews.execute(urlString);
        httpFetchNews.setOnHttpResponseEvent(new HttpListener() {
            @Override
            public void onHttpResponseEvent(List<Post> postList) {
                String allNewsStr="";
                for (Post post:postList){
                    allNewsStr+=post.toString()+"\n";
                }
                textViewTeste.setText(allNewsStr);
            }
        });



    }
}
