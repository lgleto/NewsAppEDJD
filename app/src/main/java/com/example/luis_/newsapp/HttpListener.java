package com.example.luis_.newsapp;

import java.util.List;

/**
 * Created by lourenco on 03/10/17.
 */

public interface HttpListener {

    public void onHttpResponseEvent(List<Post> postList);
}
