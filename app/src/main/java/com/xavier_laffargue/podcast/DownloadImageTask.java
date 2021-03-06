package com.xavier_laffargue.podcast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Xavier on 23/04/2015.
 */
class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
{
    String urlImg;

    public DownloadImageTask(String _urlImg)
    {
        this.urlImg = _urlImg;
        Log.d(CONF_Application.NAME_LOG, "Construct Download img");
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        Log.d(CONF_Application.NAME_LOG, "Begin Download img");
    }

    @Override
    protected Bitmap doInBackground(String... urls) {


        Log.d(CONF_Application.NAME_LOG, "Begin doInBackground");
        String urlStr = urlImg;
        Bitmap img = null;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlStr);
        HttpResponse response;
        try {
            response = (HttpResponse)client.execute(request);
            HttpEntity entity = response.getEntity();
            BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
            InputStream inputStream = bufferedEntity.getContent();
            img = getResizedBitmap(BitmapFactory.decodeStream(inputStream), 600, 600);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.d(CONF_Application.NAME_LOG, "Finish doInBackground");
        return img;
    }

    protected void onPostExecute(Bitmap result)
    {
        Log.d(CONF_Application.NAME_LOG, "Finish Download img");
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

}
