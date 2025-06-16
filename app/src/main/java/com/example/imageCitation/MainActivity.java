package com.example.imageCitation;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText input1, input2;
    private Button btnDoIt;
    private Spinner jsonSpinner;
    private String selectedJsonFile;
    private HashMap<String, String> displayNameToFileMap = new HashMap<>();
    //private Button btnColler;
    private ImageButton btnGetQuote, btnvider; // Add this new button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MainActivity");*/


        // Initialize views
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);

        //btnColler = findViewById(R.id.btnColler);
        btnGetQuote = findViewById(R.id.btnGetQuote);
        btnvider = findViewById(R.id.btnvider);
        /*
        btnColler.setOnClickListener(v -> {
            if (input1 != null) {
                input1.requestFocus();
                input1.onTextContextMenuItem(android.R.id.paste);
            }
        });*/

        btnGetQuote.setOnClickListener(v -> {
            Log.d("MainActivity", "Quote button clicked");
            fetchQuoteAsync();
        });

        btnvider.setOnClickListener( v -> {
            input1.setText("");input2.setText("");
        });

        btnDoIt = findViewById(R.id.btnDoIt);
        jsonSpinner = findViewById(R.id.spinner);

        // Set up JSON files spinner
        setupJsonSpinner();

        // Set button click listener
        btnDoIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i1 = input1.getText().toString();
                String i2 = input2.getText().toString();

                if (i1.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Donner une citation",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, Activity2.class);
                    intent.putExtra("input1", i1);
                    intent.putExtra("input2", i2);
                    intent.putExtra("selectedJson", selectedJsonFile);
                    startActivity(intent);
                }
            }
        });
    }
    private void setupJsonSpinner() {
        ArrayList<String> displayNames = new ArrayList<>();
        displayNameToFileMap.clear();

        try {
            for (String file : getAssets().list("")) {
                if (file.endsWith(".json")) {
                    String displayName = extractThemeName(file);
                    if (displayName == null || displayName.isEmpty()) {
                        displayName = file; // fallback
                    }
                    displayNames.add(displayName);
                    displayNameToFileMap.put(displayName, file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading JSON files", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                displayNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jsonSpinner.setAdapter(adapter);

        if (!displayNames.isEmpty()) {
            selectedJsonFile = displayNameToFileMap.get(displayNames.get(0));
        }

        jsonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String displayName = (String) parent.getItemAtPosition(position);
                selectedJsonFile = displayNameToFileMap.get(displayName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private String extractThemeName(String fileName) {
        try (InputStream inputStream = getAssets().open(fileName)) {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject obj = new JSONObject(json);
            return obj.optString("themeName", null);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void fetchQuoteAsync() {
        // No need for background thread since we're not doing network operations
        Log.d("MainActivity", "Getting random French quote and author");

        // Get quote and author
        QuoteFetcher.QuoteAndAuthor quoteData = QuoteFetcher.getRandomFrenchQuoteWithAuthor();

        Log.d("MainActivity", "Quote: " + quoteData.quote);
        Log.d("MainActivity", "Author: " + quoteData.author);

        // Update UI - quote in input1, author in input2
        if (input1 != null) {
            input1.setText(quoteData.quote);
            Log.d("MainActivity", "Quote set in input1");
        }

        if (input2 != null) {
            input2.setText(quoteData.author);
            Log.d("MainActivity", "Author set in input2");
        }
    }
}
