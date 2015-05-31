package com.xavier_laffargue.podcast;

/**
 * Created by Xavier on 30/05/2015.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class ACT_Player extends Activity {

    private String urlShow;

    private ImageButton buttonStop;
    private ImageButton buttonPlay;
    private TextView description;
    private ImageView imagePodcast;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        buttonStop = (ImageButton) findViewById(R.id.button_pause);
        buttonPlay = (ImageButton) findViewById(R.id.button_play);
        imagePodcast = (ImageView) findViewById(R.id.podcast_image_grand);
        description = (TextView) findViewById(R.id.text_description_player);

        ShowDataSource mesShows = new ShowDataSource(this);
        mesShows.open();

        PodcastDataSource mesPodcast = new PodcastDataSource(this);
        mesPodcast.open();


        Intent i = getIntent();
        long idShow = i.getLongExtra("idShow", 0);

        BO_Show show = mesShows.getOneShow(idShow);
        Log.d(CONF_Application.NAME_LOG, "mp3 " + show.getTitle() + " " + show.getMp3());
        Log.d(CONF_Application.NAME_LOG, "idShow =====>" + Long.toString(idShow));

        BO_Podcast pod = mesPodcast.getOnePodcast(show.getIdPodcast());


        Log.d(CONF_Application.NAME_LOG, "mp3 " + show.getTitle() + " " + show.getMp3());


        urlShow = show.getTitle();
        imagePodcast.setImageBitmap(UtilityImage.toBitmap(pod.getImage()));
        description.setText(show.getDescription());
        uri = Uri.parse(urlShow);

        Intent intent = new Intent(getApplicationContext(), MediaPlayerService.class);
        intent.putExtra("idShow", idShow);
        intent.putExtra("mp3Show", urlShow);
        intent.setAction(CONF_Application.ACTION_PLAY);
        startService(intent);






        buttonPlay.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), MediaPlayerService.class);
                intent.setAction(CONF_Application.ACTION_PLAY);
                startService(intent);
            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), MediaPlayerService.class);
                intent.setAction(CONF_Application.ACTION_PAUSE);
                startService(intent);
            }
        });
    }
}
