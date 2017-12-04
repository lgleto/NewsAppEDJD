package com.example.luis_.newsapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luis_.newsapp.model.NewsPaper;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsPaperFragment extends Fragment {

    ListView listView;
    RealmResults<NewsPaper> newsPapers;
    NewsPapersAdapter adapter;


    public NewsPaperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_newspaper, container, false);

        listView=(ListView)rootView.findViewById(R.id.listViewNewsPapers);
        adapter=new NewsPapersAdapter();
        listView.setAdapter(adapter);


        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Realm (just once per application)
        Realm.init(getContext());

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



    }

    class NewsPapersAdapter extends BaseAdapter implements View.OnClickListener{

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
            //Intent intent = new Intent(getContext(), NewsListFragment.class);
            Singleton singleton=Singleton.getInstance();
            singleton.setUrl(newsPapers.get(position).getUrl());
            singleton.setTitle(newsPapers.get(position).getTitle());

            //intent.putExtra(NewsListFragment.EXTRA_TITLE, newsPapers.get(position).getTitle());
            //intent.putExtra(NewsListFragment.EXTRA_URL, newsPapers.get(position).getUrl());

            //startActivity(intent);

            NewsListFragment newsListFragment=new NewsListFragment();
            FragmentManager fragmentManager=getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,newsListFragment)
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();

        }
    }




}
