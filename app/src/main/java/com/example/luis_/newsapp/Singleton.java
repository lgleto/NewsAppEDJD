package com.example.luis_.newsapp;

/**
 * Created by lourencogomes on 04/12/17.
 */

public class Singleton {

    private static Singleton instance = null;


    String title;
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    private Singleton(){

    }

    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }


}
