package com.example.luis_.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewFragment extends AppCompatActivity {

    WebView webView;

    String title;
    String urlStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_web_view);

        webView=(WebView)findViewById(R.id.webViewPost);

        title=getIntent().getStringExtra(NewsListFragment.EXTRA_TITLE);
        urlStr=getIntent().getStringExtra(NewsListFragment.EXTRA_URL);
        setTitle(title);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewController());
        webView.loadUrl(urlStr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionShare) {

            Intent intentShare= new Intent(Intent.ACTION_SEND);
            intentShare.setType("text/plain");
            intentShare.putExtra(Intent.EXTRA_SUBJECT,title);
            intentShare.putExtra(Intent.EXTRA_TEXT,urlStr);
            startActivity(Intent.createChooser(intentShare,"Partilhar"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class WebViewController extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
