package com.example.luis_.newsapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lourenco on 03/10/17.
 */

public class HttpFetchNews extends AsyncTask<String,Void,String> {

    List listeners= new ArrayList();

    public synchronized void setOnHttpResponseEvent(HttpListener listener){
        listeners.add(listener);
    }

    public synchronized void removeOnHttpResponseEvent(HttpListener listener){
        listeners.remove(listener);
    }

    private synchronized void fireListeners(String s){
        for (Object listener: listeners){
            ((HttpListener )listener).onHttpResponseEvent(s);
        }
    }



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
        //textViewTeste.setText(s);
        //Log.d(MainActivity.TAG, s);
        fireListeners(s);
    }
}
