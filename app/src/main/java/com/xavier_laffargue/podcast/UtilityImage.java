package com.xavier_laffargue.podcast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Xavier on 26/04/2015.
 */
public class UtilityImage {

    // convert from bitmap to byte array
    public static byte[] toBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 0, stream);


        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap toBitmap(byte[] image) {



        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}