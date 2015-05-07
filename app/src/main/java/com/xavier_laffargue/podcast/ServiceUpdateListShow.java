package com.xavier_laffargue.podcast;

import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

/**
 * Created by Xavier on 27/04/2015.
 */
public class ServiceUpdateListShow extends Service {


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {


        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

