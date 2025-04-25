package com.example.imageCitation.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;

import com.example.imageCitation.models.Template;

public class TemplateUtils {
    private static final String TAG = "TemplateUtils";

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

    public static int parseColor(String colorStr, int defaultColor) {
        try {
            return Color.parseColor(colorStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            Log.e(TAG, "Failed to parse color: " + colorStr + ". Using default.", e);
            return defaultColor;
        }
    }

    public static float parseSize(String sizeStr, float defaultSize) {
        try {
            if (sizeStr.endsWith("px")) {
                return Float.parseFloat(sizeStr.replace("px", ""));
            } else if (sizeStr.endsWith("pt")) {
                float points = Float.parseFloat(sizeStr.replace("pt", ""));
                return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, points,
                        Resources.getSystem().getDisplayMetrics());
            } else {
                throw new NumberFormatException("Unknown unit: " + sizeStr);
            }
        } catch (NumberFormatException | NullPointerException e) {
            Log.e(TAG, "Failed to parse size: " + sizeStr + ". Using default.", e);
            return defaultSize;
        }
    }

    public static Paint.Align parseAlignment(String alignStr, Paint.Align defaultAlign) {
        if (alignStr == null) return defaultAlign;
        switch (alignStr.toLowerCase()) {
            case "center": return Paint.Align.CENTER;
            case "left": return Paint.Align.LEFT;
            case "right": return Paint.Align.RIGHT;
            default:
                Log.w(TAG, "Unknown alignment: " + alignStr + ". Using default.");
                return defaultAlign;
        }
    }

    public static Typeface parseFont(Template.FontStyle fontStyle, Typeface defaultFamily, int defaultStyle) {
        if (fontStyle == null) {
            return Typeface.create(defaultFamily, defaultStyle);
        }
        String familyName = "serif"; // Default
        if (fontStyle.family != null) {
            familyName = fontStyle.family;
        }
        boolean isBold = "bold".equals(fontStyle.weight);
        boolean isItalic = fontStyle.italic;
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
}