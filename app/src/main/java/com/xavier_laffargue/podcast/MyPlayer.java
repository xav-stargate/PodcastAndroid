package com.xavier_laffargue.podcast;

import android.media.MediaPlayer;

/**
 * Created by Xavier on 30/05/2015.
 */
public class MyPlayer {
    private static MediaPlayer mPlayer = new MediaPlayer();

    public static MediaPlayer get()
    {
        return mPlayer;
    }
}
