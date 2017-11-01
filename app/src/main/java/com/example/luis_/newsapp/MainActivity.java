package com.example.luis_.newsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luis_.newsapp.model.NewsPaper;
import com.example.luis_.newsapp.model.Post;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "newsapp";

    ListView listView;
    RealmResults<NewsPaper> newsPapers;
    NewsPapersAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Realm (just once per application)
        Realm.init(this);

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        NewsPaper.add(new NewsPaper("Publico",
                "http://static.publicocdn.com/files/homepage/img/logo_share.png",
                "https://feeds.feedburner.com/PublicoRSS"),realm);
        NewsPaper.add(new NewsPaper("Observador",
                "http://img.obsnocookie.com/o=80/c=AR1.91x1/s=w770,upd1/http://observador.pt/wp-content/themes/observador/assets/build/img/og_thumb.jpg",
                "http://feeds.feedburner.com/obs-ultimas"),realm);
        NewsPaper.add(new NewsPaper("Record",
                "http://www.record.pt/img/recordLogoShare.jpg",
                "http://www.record.pt/rss.aspx"),realm);
        NewsPaper.add(new NewsPaper("Todas as Noticias",
                "",
                ""),realm);

        newsPapers=realm.where(NewsPaper.class).findAll();

        listView=(ListView)findViewById(R.id.listViewNewsPapers);
        adapter=new NewsPapersAdapter();
        listView.setAdapter(adapter);

    }

    class NewsPapersAdapter extends BaseAdapter  implements View.OnClickListener{

        @Override
        public int getCount() {
            return newsPapers.size();
        }

        @Override
        public Object getItem(int i) {
            return newsPapers.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view==null){
                view = getLayoutInflater().inflate(R.layout.row_post,null);
            }

            TextView textView = (TextView)view.findViewById(R.id.textViewPostTilte);
            final ImageView imageView =(ImageView)view.findViewById(R.id.imageView);

            textView.setText(newsPapers.get(i).getTitle());


            final String urlImage= newsPapers.get(i).getUrlImage();

            new AsyncTask<String,Void,Bitmap>(){

                @Override
                protected Bitmap doInBackground(String... strings) {

                    return Utils.getBitmapFromURL(urlImage);
                }

                @Override
                protected void onPostExecute(Bitmap bm) {
                    super.onPostExecute(bm);
                    imageView.setImageBitmap(bm);
                }
            }.execute(null,null,null);


            view.setTag(new Integer(i));
            view.setOnClickListener(this);
            view.setClickable(true);

            return view;
        }

        @Override
        public void onClick(View view) {
            Integer position= (Integer) view.getTag();
            Intent intent = new Intent(MainActivity.this, NewsListActivity.class);
            intent.putExtra(NewsListActivity.EXTRA_TITLE, newsPapers.get(position).getTitle());
            intent.putExtra(NewsListActivity.EXTRA_URL, newsPapers.get(position).getUrl());
            startActivity(intent);
        }
    }




    @Override
    public void onClick(View view) {
        String url;
        String title;
        url = "https://feeds.feedburner.com/PublicoRSS";
        title = "Público";
        url = "http://www.record.pt/rss.aspx";
        title = "Record";
        Intent intent = new Intent(this, NewsListActivity.class);
        intent.putExtra(NewsListActivity.EXTRA_TITLE, title);
        intent.putExtra(NewsListActivity.EXTRA_URL, url);
        startActivity(intent);
    }

}
