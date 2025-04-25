package com.example.imageCitation;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityPlus extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Explications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Configure WebView
        webView = findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/plus.html");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Handle back button in toolbar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}