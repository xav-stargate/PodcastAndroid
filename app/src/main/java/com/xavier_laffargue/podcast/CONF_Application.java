package com.xavier_laffargue.podcast;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.InetAddress;

/**
 * Created by Xavier on 08/05/2015.
 */
public class CONF_Application {
    public static final String NAME_LOG = "podcastAndroidX";
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_REWIND = "action_rewind";
    public static final String ACTION_FAST_FORWARD = "action_fast_foward";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_STOP = "action_stop";


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            Log.d(CONF_Application.NAME_LOG, "Pas de connexion");
            return false;
        } else
            Log.d(CONF_Application.NAME_LOG, "OK connexion");
            return isInternetAvailable();
    }

    private static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("37.187.47.89"); //You can replace it with your name
            Log.d(CONF_Application.NAME_LOG, "OK ipAddr");
            if (ipAddr.equals("")) {
                Log.d(CONF_Application.NAME_LOG, "Pas de connexion internet");
                return false;
            } else {
                Log.d(CONF_Application.NAME_LOG, "OK connexion internet");
                return true;
            }

        } catch (Exception e) {
            Log.d(CONF_Application.NAME_LOG, "Exception !! " + e.getMessage());
            return false;
        }

    }

}
