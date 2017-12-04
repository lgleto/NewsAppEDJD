package com.example.luis_.newsapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.luis_.newsapp.model.NewsPaper;
import com.example.luis_.newsapp.model.Post;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class NewsListFragment extends Fragment {

    public static final String EXTRA_URL = "extra_url";
    public static final String EXTRA_TITLE = "extra_title";

    ListView listViewPosts;
    PostsAdapter adapter;

    RealmResults<Post> posts; //model
    NewsPaper newsPaper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        listViewPosts=(ListView)rootView.findViewById(R.id.listViewPosts);
        adapter=new PostsAdapter();
        listViewPosts.setAdapter(adapter);

        return rootView;
    }

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title =  Singleton.getInstance().getTitle();//getIntent().getStringExtra(EXTRA_TITLE);
        final String urlString = Singleton.getInstance().getUrl();//getIntent().getStringExtra(EXTRA_URL);
        getActivity().setTitle(title);

        // Get a Realm instance for this thread
        final Realm realm = Realm.getDefaultInstance();
        posts = realm.where(Post.class).equalTo("newspaper.url",urlString).findAll();
        newsPaper = realm.where(NewsPaper.class).equalTo("url",urlString).findAll().first();



        HttpFetchNews httpFetchNews = new HttpFetchNews();
        httpFetchNews.execute(urlString);
        httpFetchNews.setOnHttpResponseEvent(new HttpListener() {
            @Override
            public void onHttpResponseEvent(List<Post> postList) {
                for (Post post:postList){
                    //alterado fora da aula
                    Post.add(post,newsPaper,realm);
                }
                //alterado fora da aula
                posts = realm.where(Post.class).equalTo("newspaper.url",urlString).findAll();
                adapter.notifyDataSetChanged();
            }
        });
    }

    class PostsAdapter extends BaseAdapter implements View.OnClickListener{

        LayoutInflater layoutInflater;

        public  PostsAdapter(){
            layoutInflater=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return posts.size();
        }

        @Override
        public Object getItem(int i) {
            return posts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = layoutInflater.inflate(R.layout.row_post,null);

            TextView textViewTitle = (TextView)view.findViewById(R.id.textViewPostTilte);
            textViewTitle.setText(posts.get(i).getTitle());
            final ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
            final String urlImage= posts.get(i).getImageLink();
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

            Integer position=(Integer) view.getTag();
            Log.d(MainActivity.TAG, posts.get(position).getTitle());

            Intent intent = new Intent(getActivity(),WebViewFragment.class);
            intent.putExtra(NewsListFragment.EXTRA_TITLE, posts.get(position).getTitle());
            intent.putExtra(NewsListFragment.EXTRA_URL, posts.get(position).getUrl());
            startActivity(intent);

        }
    }


}
