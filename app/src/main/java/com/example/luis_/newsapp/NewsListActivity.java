package com.example.luis_.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        String title = getIntent().getStringExtra("extra_title");
        String urlString = getIntent().getStringExtra("extra_url");
        setTitle(title);
        final TextView textViewTeste = (TextView)findViewById(R.id.textViewTeste);
        //textViewTeste.setText(url);

        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... strings) {

                String result = "";
                HttpURLConnection urlConnection=null;
                try {
                    URL url = new URL(strings[0]);
                    urlConnection=(HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setRequestMethod("GET");

                    InputStream inputStream= urlConnection.getInputStream();
                    BufferedReader bufferedReader=
                            new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder=new StringBuilder();
                    String line;

                    while((line = bufferedReader.readLine())!=null){
                        stringBuilder.append(line).append('\n');
                    }

                    result = stringBuilder.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    urlConnection.disconnect();
                }

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                textViewTeste.setText(s);
            }
        }.execute(urlString);


    }
}
