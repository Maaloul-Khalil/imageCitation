package com.example.imageCitation.models;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;

public class Template {
    private static final String TAG = "Template";
    public Text text;
    public String background;
    public String themeName;

    public String getThemeName() {
        return themeName;
    }
    public String getBackground() {
        return background;
    }

    public float getQuoteX(float canvasWidth) {
        return parsePosition(text.quote.position.x, canvasWidth);
    }

    public float getQuoteY(float canvasHeight) {
        return parsePosition(text.quote.position.y, canvasHeight);
    }

    public int getQuoteColor() {
        return parseColor(text.quote.style.color, Color.WHITE);
    }

    public float getQuoteSize() {
        return parseSize(text.quote.style.size, 60f);
    }

    public Typeface getQuoteTypeface() {
        return parseFont(text.quote.style.font, Typeface.SERIF, Typeface.BOLD);
    }

    public QuotationMarkSettings getQuoteQuotationMarkSettings() {
        return text.quote.style.quotation_marks != null ?
                text.quote.style.quotation_marks : new QuotationMarkSettings();
    }

    public float getAuthorX(float canvasWidth) {
        return parsePosition(text.author.position.x, canvasWidth);
    }

    public float getAuthorY(float canvasHeight) {
        return parsePosition(text.author.position.y, canvasHeight);
    }

    public int getAuthorColor() {
        return parseColor(text.author.style.color, Color.LTGRAY);
    }

    public float getAuthorSize() {
        return parseSize(text.author.style.size, 40f);
    }

    public String getAuthorPrefix() {
        return text.author.style.prefix != null ? text.author.style.prefix : "";
    }

    public Typeface getAuthorTypeface() {
        return parseFont(text.author.style.font, Typeface.SANS_SERIF, Typeface.ITALIC);
    }

    // Helper methods consolidated in Template class
    public static int parseColor(String colorString, int defaultColor) {
        try {
            return (colorString != null) ? Color.parseColor(colorString) : defaultColor;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Failed to parse color: " + colorString + ". Using default.", e);
            return defaultColor;
        }
    }

    public float parseSize(String sizeStr, float defaultSize) {
        try {
            if (sizeStr.endsWith("px")) {
                // Remove "px" and parse as pixels
                return Float.parseFloat(sizeStr.replace("px", ""));
            } else if (sizeStr.endsWith("pt")) {
                // Remove "pt" and parse as points, then convert to pixels
                float points = Float.parseFloat(sizeStr.replace("pt", ""));
                return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, points, Resources.getSystem().getDisplayMetrics());
            } else {
                throw new NumberFormatException("Unknown unit: " + sizeStr);
            }
        } catch (NumberFormatException | NullPointerException e) {
            Log.e("TemplateParse", "Failed to parse size: " + sizeStr + ". Using default.", e);
            return defaultSize;
        }
    }

    public static Typeface parseFont(FontStyle fontStyle, Typeface defaultFamily, int defaultStyle) {
        // Return default if no font style specified
        if (fontStyle == null) {
            return Typeface.create(defaultFamily, defaultStyle);
        }

        // Determine font family
        String familyName = "serif"; // Default
        if (fontStyle.family != null) {
            familyName = fontStyle.family;
        }

        // Check for bold (direct string comparison)
        boolean isBold = "bold".equals(fontStyle.weight);
        boolean isItalic = fontStyle.italic;

        // Determine style
        int style = Typeface.NORMAL;
        if (isBold && isItalic) {
            style = Typeface.BOLD_ITALIC;
        } else if (isBold) {
            style = Typeface.BOLD;
        } else if (isItalic) {
            style = Typeface.ITALIC;
        }

        return Typeface.create(familyName, style);
    }

    public static float parsePosition(String pos, float dimension) {
        try {
            if (pos.endsWith("%")) {
                float percentage = Float.parseFloat(pos.substring(0, pos.length() - 1));
                return (percentage / 100f) * dimension;
            } else {
                // Assume pixels if no %
                return Float.parseFloat(pos.replace("px", ""));
            }
        } catch (NumberFormatException | NullPointerException e) {
            Log.e(TAG, "Failed to parse position: " + pos + ". Using 0.5*dimension.", e);
            return 0.5f * dimension; // Default center
        }
    }

    // Nested classes
    public static class Text {
        public Quote quote;
        public Author author;
    }

    public static class Quote {
        public Position position;
        public QuoteStyle style;
    }

    public static class Author {
        public Position position;
        public AuthorStyle style;
    }

    public static class Position {
        public String x;
        public String y;
    }

    public static class QuoteStyle {
        public String color;
        public String size;
        public QuotationMarkSettings quotation_marks;
        public FontStyle font;
    }

    public static class AuthorStyle {
        public String color;
        public String size;
        public String alignment;
        public String prefix;
        public FontStyle font;
    }

    public static class FontStyle {
        public String family;
        public String weight;
        public boolean italic;
    }

    public static class QuotationMarkSettings {
        public boolean enabled = false;
        public String style = "none";
        public float size_multiplier = 1.0f;
        public float vertical_offset = 0f;
        public String color;

        public String getOpenQuote() {
            if (!enabled) return "";
            switch (style) {
                case "double_curved": return "“";
                case "single_curved": return "‘";
                case "single_angle": return "‹";
                case "guillemets": return "«";
                case "japanese": return "「";
                case "none":
                default: return "";
            }
        }

        public String getCloseQuote() {
            if (!enabled) return "";
            switch (style) {
                case "double_curved": return "”";
                case "single_curved": return "’";
                case "single_angle": return "›";
                case "guillemets": return "»";
                case "japanese": return "」";
                case "none":
                default: return "";
            }
        }
    }
}