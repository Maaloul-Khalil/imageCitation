package com.example.imageCitation.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imageCitation.models.Template;

public class QuoteRenderer {
    private static final String TAG = "QuoteRenderer";
    private Context context;
    private ImageView imageView;
    private String quote;
    private String author;

    public QuoteRenderer(Context context, ImageView imageView, String quote, String author) {
        this.context = context;
        this.imageView = imageView;
        this.quote = quote;
        this.author = author;
    }

    public Bitmap generateQuoteImage(Template template) {
        // Ensure template is loaded
        if (template == null) {
            Log.e(TAG, "Template erreur");
            Toast.makeText(context, "erreur dans generation image", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Load background
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        String imageName = template.getBackground();
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        Bitmap backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);

        if (backgroundBitmap == null) {
            Log.e(TAG, "erreur dans image background");
            // Create a solid color background if loading fails
            int width = imageView.getWidth() > 0 ? imageView.getWidth() : 800;
            int height = imageView.getHeight() > 0 ? imageView.getHeight() : 600;
            backgroundBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas placeholderCanvas = new Canvas(backgroundBitmap);
            placeholderCanvas.drawColor(Color.DKGRAY); // Dark gray placeholder
        }

        // Create a mutable copy of the background bitmap
        Bitmap outputBitmap = backgroundBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // Clean up the original to save memory
        if (backgroundBitmap != outputBitmap) {
            backgroundBitmap.recycle();
        }

        // Create canvas for drawing on the bitmap
        Canvas canvas = new Canvas(outputBitmap);

        // Draw the quote text on the background
        drawQuoteOnImage(canvas, template);

        Log.d(TAG, "dimensions Quote image: " +
                outputBitmap.getWidth() + "x" + outputBitmap.getHeight());

        return outputBitmap;
    }

    private void drawQuoteOnImage(Canvas canvas, Template template) {
        // Quote getter
        Paint quotePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        quotePaint.setColor(template.getQuoteColor());
        quotePaint.setTextSize(template.getQuoteSize());
        quotePaint.setTypeface(template.getQuoteTypeface());
        quotePaint.setTextAlign(Paint.Align.CENTER);

        float quoteX = template.getQuoteX(canvas.getWidth());
        float quoteY = template.getQuoteY(canvas.getHeight());
        float maxTextWidth = canvas.getWidth() * 0.85f; //85% canvas

        Template.QuotationMarkSettings qmSettings = template.getQuoteQuotationMarkSettings();
        String openQuote = qmSettings.getOpenQuote();
        String closeQuote = qmSettings.getCloseQuote();
        String formattedQuote = Character.toUpperCase(quote.charAt(0)) + quote.substring(1);
        String quoteTextToDraw = openQuote + formattedQuote + closeQuote;

        // Draw quote multilige
        drawMultilineText(canvas, quoteTextToDraw, quoteX, quoteY, quotePaint, maxTextWidth, qmSettings);

        // author getter
        Paint authorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        authorPaint.setColor(template.getAuthorColor());
        authorPaint.setTextSize(template.getAuthorSize());
        authorPaint.setTypeface(template.getAuthorTypeface());
        authorPaint.setTextAlign(Paint.Align.CENTER);

        float authorX = template.getAuthorX(canvas.getWidth());
        float authorY = template.getAuthorY(canvas.getHeight());
        // ajoute prefix
        String authorPrefix = template.getAuthorPrefix();
        String authorTextToDraw = authorPrefix + this.author;

        // Draw author (single line assumed for author)
        canvas.drawText(authorTextToDraw, authorX, authorY, authorPaint);

        Log.d(TAG, "Drawn quote at: " + quoteX + "," + quoteY +
                " | Author at: " + authorX + "," + authorY);
    }

    private void drawMultilineText(Canvas canvas, String text, float x, float y, Paint paint,
                                   float maxWidth, Template.QuotationMarkSettings qmSettings) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        float lineHeight = paint.getFontSpacing();
        float currentY = y;
        // Initialize quoteMarkPaint only if needed
        Paint quoteMarkPaint;
        if (qmSettings.enabled && (qmSettings.size_multiplier != 1.0f || qmSettings.color != null)) {
            quoteMarkPaint = new Paint(paint);
            quoteMarkPaint.setTextSize(paint.getTextSize() * qmSettings.size_multiplier);
            quoteMarkPaint.setColor(Color.parseColor(qmSettings.color));
        }
        for (String word : words) {
            String testLine = (line.length() == 0) ? word : line + " " + word;
            float testWidth = paint.measureText(testLine);
            if (testWidth > maxWidth && line.length() > 0) {
                canvas.drawText(line.toString(), x, currentY, paint);
                line = new StringBuilder(word);
                currentY += lineHeight;
            } else {
                if (line.length() > 0) line.append(" ");
                line.append(word);
            }
        }
        // Draw remaining text
        if (line.length() > 0) {
            canvas.drawText(line.toString(), x, currentY, paint);
        }
    }
}