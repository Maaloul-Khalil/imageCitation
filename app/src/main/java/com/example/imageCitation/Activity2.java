package com.example.imageCitation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.example.imageCitation.graphics.QuoteRenderer;
import com.example.imageCitation.managers.TemplateManager;
import com.example.imageCitation.models.Template;
import com.example.imageCitation.utils.BtnImg;

public class Activity2 extends AppCompatActivity {
    public static final String TAG = "Activity2";
    public ImageView imageViewQuote;
    public String quote, author;
    public Bitmap outputBitmap;
    private Template currentTemplate;
    private Button btnSauvegardez, btnCopiez;
    private QuoteRenderer quoteRenderer;
    private TemplateManager templateManager;
    private BtnImg btnImgUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Output");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        this.quote = intent.getStringExtra("input1");

        this.author = intent.getStringExtra("input2");
        if (this.author.isEmpty()) {
            this.author = "Anonyme"; // Default author
        }

        String selectedJson = getIntent().getStringExtra("selectedJson");

        imageViewQuote = findViewById(R.id.image_view_quote);
        btnSauvegardez = findViewById(R.id.btnSauvegardez);
        btnCopiez = findViewById(R.id.btnCopiez);

        // Initialize utility classes
        templateManager = new TemplateManager(this);
        btnImgUtils = new BtnImg(this, imageViewQuote);

        // Load template
        currentTemplate = templateManager.loadTemplateFromJson(selectedJson);
        if (currentTemplate == null) {
            Log.e(TAG, "Failed to load template, using default.");
            currentTemplate = templateManager.createDefaultTemplate();
        }

        // Initialize renderer after we have quote, author and template
        quoteRenderer = new QuoteRenderer(this, imageViewQuote, quote, author);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // aprés images est généré
        imageViewQuote.post(() -> {
            outputBitmap = quoteRenderer.generateQuoteImage(currentTemplate);
            imageViewQuote.setImageBitmap(outputBitmap);
            // les boutons
            btnImgUtils.setupSaveButton(btnSauvegardez);
            btnImgUtils.setupCopyButton(btnCopiez);
        });
    }

    // ajoute an icon dans toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // back button
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        // custom button
        if (id == R.id.btngoogle) {
            /*
            String internet = "https://developer.android.com/develop/ui/views/layout/custom-views/custom-drawing";
            Intent intentInternet = new Intent(Intent.ACTION_VIEW, Uri.parse(internet));
            startActivity(intentInternet);
            return true;
             */
            Intent intent = new Intent(Activity2.this, ActivityPlus.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}