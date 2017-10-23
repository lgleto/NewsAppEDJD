package com.example.luis_.newsapp.model;

import java.util.Date;

import io.realm.RealmObject;

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
}
