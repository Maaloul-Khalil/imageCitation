package com.example.imageCitation.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BtnImg {
    private static final String TAG = "BtnImg";
    private Context context;
    private ImageView imageView;

    public BtnImg(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    public void setupSaveButton(Button button) {
        button.setOnClickListener(v -> {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            saveImageToGallery(bitmap);
        });
    }

    public void saveImageToGallery(Bitmap bitmap) {
        String filename = "Quote_" + System.currentTimeMillis() + ".png";

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        try {
            Uri imageUri = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
            );

            if (imageUri != null) {
                try (OutputStream outputStream = context.getContentResolver().openOutputStream(imageUri)) {
                    if (outputStream != null) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        Toast.makeText(context, "Image enregistrée dans la galerie !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error saving image: " + e.getMessage());
            Toast.makeText(context, "Échec de l'enregistrement de l'image", Toast.LENGTH_SHORT).show();
        }
    }

    public void setupCopyButton(Button button) {
        button.setOnClickListener(v -> {
            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            copyImageToClipboard(bitmap);
        });
    }

    public void copyImageToClipboard(Bitmap bitmap) {
        try {
            // 1. Save bitmap to cache
            Uri imageUri = saveBitmapToCache(bitmap);

            // 2. Create clipboard item
            ClipData clip = ClipData.newUri(
                    context.getContentResolver(),
                    "Quote Image",
                    imageUri
            );

            // 3. Copy to clipboard
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(context, "Image copiée !", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Échec de la copie", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Échec de la copie", e);
        }
    }

    private Uri saveBitmapToCache(Bitmap bitmap) throws IOException {
        File cacheDir = new File(context.getCacheDir(), "clipboard");
        if (!cacheDir.exists()) cacheDir.mkdirs();

        File file = new File(cacheDir, "quote_" + System.currentTimeMillis() + ".png");
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }

        return FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".fileprovider",
                file
        );
    }
}