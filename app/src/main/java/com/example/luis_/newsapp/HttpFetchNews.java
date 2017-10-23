package com.example.luis_.newsapp;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by lourenco on 03/10/17.
 */

public class HttpFetchNews extends AsyncTask<String,Void,List<Post>> {

    List listeners= new ArrayList();

    public synchronized void setOnHttpResponseEvent(HttpListener listener){
        listeners.add(listener);
    }

    public synchronized void removeOnHttpResponseEvent(HttpListener listener){
        listeners.remove(listener);
    }

    private synchronized void fireListeners(List<Post> postList){
        for (Object listener: listeners){
            ((HttpListener )listener).onHttpResponseEvent(postList);
        }
    }

    @Override
    protected List<Post> doInBackground(String... strings) {
        List<Post> postList= new ArrayList<Post>();

        HttpURLConnection urlConnection=null;
        try {
            URL url = new URL(strings[0]);
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");

            InputStream inputStream= urlConnection.getInputStream();

            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder= factory.newDocumentBuilder();
            Document doc=builder.parse(inputStream);

            NodeList nodeList = doc.getElementsByTagName("item");

            for (int i=0; i< nodeList.getLength(); i++){
                Element element=(Element) nodeList.item(i);
                NodeList nodeTitle=element.getElementsByTagName("title");
                String title=nodeTitle.item(0).getTextContent();
                NodeList nodeLink=element.getElementsByTagName("link");
                String link=nodeLink.item(0).getTextContent();

                NodeList nodeImage=element.getElementsByTagName("enclosure");
                String imageUrl="";
                if(nodeImage.item(0)!=null)
                if (nodeImage.item(0).getAttributes().getLength()>0) {
                    imageUrl = nodeImage.item(0).getAttributes().getNamedItem("url").getTextContent();
                    imageUrl="http:"+imageUrl;
                }


                Post post=new Post();
                post.setTitle(title);
                post.setUrl(link);
                post.setImageLink(imageUrl);
                postList.add(post);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return postList;
    }

    @Override
    protected void onPostExecute(List<Post> postList) {
        super.onPostExecute(postList);
        //textViewTeste.setText(s);
        //Log.d(MainActivity.TAG, s);
        fireListeners(postList);
    }
}
