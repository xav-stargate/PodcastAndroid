package com.xavier_laffargue.podcast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class ACT_AddPodcast extends Activity {

    private Button ajouter;
    private EditText url;
    private PodcastDataSource mesPodcast;
    private BO_Podcast podcastDownloaded;
    private ShowDataSource mesShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_add_podcast);

        ajouter = (Button)   findViewById(R.id.btn_ajouter_podcast);
        url     = (EditText) findViewById(R.id.txt_url_addPodcast);

        mesPodcast = new PodcastDataSource(this);

        mesPodcast.open();

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPodcastXML(url.getText().toString());
            }
        });
    }


    public void addPodcastXML(final String urlFileXML) {

        mesShow = new ShowDataSource(this);
        mesShow.open();

        DownloadReadXmlTask pod = new DownloadReadXmlTask(this, new Callback(){
            public void run(Object result){
                //Enregistrement dans la base

                podcastDownloaded = (BO_Podcast)result;


                mesPodcast.ajouterPodcast(podcastDownloaded, mesShow);

            }});
        pod.execute(urlFileXML);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_add_podcast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                openAdd();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openAdd() {
        startActivity(new Intent(this, ACT_AddPodcast.class));
    }

    public void openSettings() {
        startActivity(new Intent(this, ACT_SettingPodcast.class));
    }
}
