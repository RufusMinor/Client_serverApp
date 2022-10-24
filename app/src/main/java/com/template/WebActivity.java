package com.template;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {
    private WebView webview;
    public String nameAdress, text;
    public TextView textWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent2 = getIntent();
        if (intent2 != null) {
            Log.d("Log1", "Готовая ссылка: " + intent2.getStringExtra("String"));
            nameAdress=intent2.getStringExtra("String");

        }


        //textWeb = (TextView) findViewById(R.id.textView2);
        //textWeb.setText(nameAdress);
        webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(nameAdress);
        webview.setWebViewClient(new WebViewClient());

    }


    class WebViewClient extends android.webkit.WebViewClient {

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
