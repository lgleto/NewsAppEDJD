package com.example.luis_.newsapp.model;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by lourenco on 09/10/17.
 */

public class Post  extends RealmObject {

    String title;
    String url;
    Date date;
    String imageLink;
    String description;
    NewsPaper newspaper;

    public Post() {
    }

    public Post(String title, String url, Date date, String imageLink, String description) {
        this.title = title;
        this.url = url;
        this.date = date;
        this.imageLink = imageLink;
        this.description = description;
    }
    public NewsPaper getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(NewsPaper newspaper) {
        this.newspaper = newspaper;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", date=" + date +
                ", imageLink='" + imageLink + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static void add (final Post post, /*alterado fora da aula*/ final NewsPaper newsPaper, Realm realm){
        RealmResults<Post> posts=
                realm.where(Post.class).equalTo("url",post.getUrl()).findAll();
        if (posts.size()==0){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Post newPost= realm.createObject(Post.class);
                    newPost.setDate(post.getDate());
                    newPost.setDescription(post.getDescription());
                    newPost.setImageLink(post.getImageLink());
                    newPost.setTitle(post.getTitle());
                    newPost.setNewspaper(post.getNewspaper());
                    newPost.setUrl(post.getUrl());
                    /*alterado fora da aula*/
                    newPost.setNewspaper(newsPaper);
                }
            });
        }else {
            update(post, /*alterado fora da aula*/newsPaper, realm);
        }
    }

    public static void update(final Post post, final NewsPaper newsPaper, Realm realm){
        final RealmResults<Post> posts=
                realm.where(Post.class).equalTo("url",post.getUrl()).findAll();

        if (posts.size()>0){
             /*alterado fora da aula*/
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    posts.first().setDate(post.getDate());
                    posts.first().setDescription(post.getDescription());
                    posts.first().setImageLink(post.getImageLink());
                    posts.first().setTitle(post.getTitle());
                    posts.first().setNewspaper(post.getNewspaper());
                    posts.first().setUrl(post.getUrl());
                    posts.first().setNewspaper(newsPaper);
                }
            });


        }
    }
}

