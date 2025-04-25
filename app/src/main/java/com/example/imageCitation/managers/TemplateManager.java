package com.example.imageCitation.managers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.imageCitation.models.Template;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

public class TemplateManager {
    private static final String TAG = "TemplateManager";
    private Context context;

    public TemplateManager(Context context) {
        this.context = context;
    }

    public Template loadTemplateFromJson(String fileName) {
        try (InputStream inputStream = context.getAssets().open(fileName);
             InputStreamReader reader = new InputStreamReader(inputStream)) {

            Gson gson = new Gson();
            Template template = gson.fromJson(reader, Template.class);
            if (template == null) {
                throw new Exception("erreur parse JSON");
            }

            Log.d(TAG, "Template complete: " + fileName);
            return template;

        } catch (Exception e) {
            Log.e(TAG, "erreur parse JSON: " + fileName, e);
            Toast.makeText(context, "erreur de generation", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public Template createDefaultTemplate() {
        Template template = new Template();
        template.text = new Template.Text();
        template.text.quote = new Template.Quote();
        template.text.author = new Template.Author();

        template.text.quote.position = new Template.Position();
        template.text.quote.position.x = "50%";
        template.text.quote.position.y = "40%";

        template.text.quote.style = new Template.QuoteStyle();
        template.text.quote.style.color = "#FFFFFF";
        template.text.quote.style.size = "60px";
        template.text.quote.style.font = new Template.FontStyle();
        template.text.quote.style.font.family = "serif";
        template.text.quote.style.font.weight = "bold";
        template.text.quote.style.font.italic = false;
        template.text.quote.style.quotation_marks = new Template.QuotationMarkSettings();

        template.text.author.position = new Template.Position();
        template.text.author.position.x = "50%";
        template.text.author.position.y = "85%";

        template.text.author.style = new Template.AuthorStyle();
        template.text.author.style.color = "#DDDDDD";
        template.text.author.style.size = "40px";
        template.text.author.style.alignment = "center";
        template.text.author.style.prefix = "- ";
        template.text.author.style.font = new Template.FontStyle();
        template.text.author.style.font.family = "sans-serif";
        template.text.author.style.font.weight = "normal";
        template.text.author.style.font.italic = true;

        // Set a default background
        template.background = "background_image";

        return template;
    }
}
