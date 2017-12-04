package com.example.luis_.newsapp;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity  {

    public static final String TAG = "newsapp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsPaperFragment newsPaperFragment=new NewsPaperFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,newsPaperFragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }

}
