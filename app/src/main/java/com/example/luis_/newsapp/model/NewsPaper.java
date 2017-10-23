package com.example.luis_.newsapp.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by lourenco on 23/10/17.
 */

public class NewsPaper extends RealmObject {

    String title;
    String url;

    RealmList<Post> posts;

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

}
