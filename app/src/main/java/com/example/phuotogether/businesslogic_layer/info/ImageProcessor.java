package com.example.phuotogether.businesslogic_layer.info;


import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageProcessor {
    public static void saveBitmapAsPng(Bitmap bitmap, String filename) {
        File file = new File(Environment.getExternalStorageDirectory(), filename + ".png");

        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(CompressFormat.PNG, 100, out); // PNG format, quality 100%
            // You can also use CompressFormat.JPEG for JPEG format

            // Notify the system that a new file was created
            out.flush();
            out.getFD().sync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
