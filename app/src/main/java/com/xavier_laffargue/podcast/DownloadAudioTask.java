package com.xavier_laffargue.podcast;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Xavier on 24/04/2015.
 */
public class DownloadAudioTask {

    private String audioFileURL;

    public DownloadAudioTask(String _audioFileURL)
    {
        this.audioFileURL = _audioFileURL;

        Log.d(CONF_Application.NAME_LOG, "construct audio file url");
    }


    public void download(String fileName) {

        try {
            URL url = new URL(audioFileURL);
            File file = new File(fileName);

            Log.d(CONF_Application.NAME_LOG, "download begining");
            Log.d(CONF_Application.NAME_LOG, "download url:" + url);
            Log.d(CONF_Application.NAME_LOG, "downloaded file name:" + fileName);

            /* Open a connection to that URL. */
            URLConnection con = url.openConnection();

            InputStream is = con.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, 1024 * 50);
            FileOutputStream fos = new FileOutputStream("/sdcard/" + file);
            byte[] buffer = new byte[1024 * 50];

            int current = 0;
            while ((current = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, current);
            }

            fos.flush();
            fos.close();
            bis.close();

        } catch (IOException e) {
            Log.d(CONF_Application.NAME_LOG, "Error: " + e);
        }

    }
}