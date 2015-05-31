package com.xavier_laffargue.podcast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Xavier on 12/05/2015.
 */
class DownloadReadXmlTask extends AsyncTask<String, Void, BO_Podcast>
{
    Callback callback;
    BO_Podcast podcast;
    ProgressDialog pDialog;


    public DownloadReadXmlTask(Activity _context, Callback call)
    {
        Log.d(CONF_Application.NAME_LOG, "Construct Download xml");

        callback = call;
        pDialog = new ProgressDialog(_context);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Ajout du podcast...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pDialog.show();
        Log.d(CONF_Application.NAME_LOG, "Begin Download img");
    }



    protected BO_Podcast doInBackground(String... urls) {


        podcast = null;

        try
        {
            XmlParser monPodcast = new XmlParser(new URL(urls[0]));
            podcast = monPodcast.getPodcast();
            podcast.setUrlXML(urls[0]);
        }
        catch (MalformedURLException ex)
        {
            Log.d(CONF_Application.NAME_LOG, "Erreur au niveau de la forme de l'url");
        }


        try
        {
            DownloadImageTask d = new DownloadImageTask(podcast.getUrlImage());
            Log.d(CONF_Application.NAME_LOG, podcast.getUrlImage());
            d.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            podcast.setImage(UtilityImage.toBytes(d.get()));

        }
        catch(InterruptedException ex)
        {
            Log.d(CONF_Application.NAME_LOG, "InterruptedException " + ex.toString());
        }
        catch (ExecutionException ex)
        {
            Log.d(CONF_Application.NAME_LOG, "ExecutionException " + ex.toString());
        }



        return podcast;
    }

    protected void onPostExecute(BO_Podcast result)
    {
        pDialog.dismiss();
        callback.run(result);
    }

}

