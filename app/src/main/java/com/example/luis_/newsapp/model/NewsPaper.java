package com.example.luis_.newsapp.model;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by lourenco on 23/10/17.
 */

public class NewsPaper extends RealmObject {

    String title;
    String urlImage;
    String url;

    public NewsPaper(String title, String urlImage, String url) {
        this.title = title;
        this.urlImage = urlImage;
        this.url = url;
    }

    public NewsPaper(){

    }

    RealmList<Post> posts;

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RealmList<Post> getPosts() {
        return posts;
    }

    public void setPosts(RealmList<Post> posts) {
        this.posts = posts;
    }

    public static void add (final NewsPaper newsPaper, Realm realm){
        RealmResults<NewsPaper> newsPapers=
                realm.where(NewsPaper.class).equalTo("url",newsPaper.getUrl()).findAll();
        if (newsPapers.size()==0){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    NewsPaper newNewsPaper= realm.createObject(NewsPaper.class);
                    newNewsPaper.setTitle(newsPaper.getTitle());
                    newNewsPaper.setUrlImage(newsPaper.getUrlImage());
                    newNewsPaper.setUrl(newsPaper.getUrl());
                }
            });
        }else {
            update(newsPaper, realm);
        }
    }

    public static void update(final NewsPaper newsPaper, Realm realm){
        final RealmResults<NewsPaper> newsPapers=
                realm.where(NewsPaper.class).equalTo("url",newsPaper.getUrl()).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (newsPapers.size()>0){
                    newsPapers.first().setTitle(newsPaper.getTitle());
                    newsPapers.first().setUrlImage(newsPaper.getUrlImage());
                    newsPapers.first().setUrl(newsPaper.getUrl());
                }
            }
        });

    }



}
