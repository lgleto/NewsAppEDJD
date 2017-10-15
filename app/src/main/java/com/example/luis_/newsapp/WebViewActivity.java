package com.example.luis_.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView=(WebView)findViewById(R.id.webViewPost);

        String title=getIntent().getStringExtra(NewsListActivity.EXTRA_TITLE);
        String urlStr=getIntent().getStringExtra(NewsListActivity.EXTRA_URL);
        setTitle(title);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewController());
        webView.loadUrl(urlStr);
    }

    class WebViewController extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
