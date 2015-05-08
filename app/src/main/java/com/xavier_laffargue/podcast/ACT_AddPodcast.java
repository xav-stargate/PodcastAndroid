package com.xavier_laffargue.podcast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class ACT_AddPodcast extends Activity {

    private Button ajouter;
    private PodcastDataSource mesPodcast;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_add_podcast);

        ajouter = (Button)findViewById(R.id.btn_ajouter_podcast);

        mesPodcast = new PodcastDataSource(this);
        pDialog = new ProgressDialog(ACT_AddPodcast.this);

        mesPodcast.open();

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPodcastXML("http://example.com/file.xml");
            }
        });
    }


    public void addPodcastXML(String urlFileXML) {
        //Téléchargement du fichier XML


        //Parsage du XML
        final String imagPodcast = "http://media.radiofrance-podcast.net/podcast09/RF_OMM_0000009471_ITE.jpg";
        final String namePodcast = "A ton âge";
        final String descPodcast = "durée : 00:08:47 - A ton age - par : Caroline Gilet - Anne et Pascal sont 'nés dans les pommes de terre', se sont mariés et on travaillé 28 ans côte à côte dans leur ferme de Lumigny. Aujourd'hui, la retraite approche et ils s'apprêtent à transmettre l'exploitation à un de leurs fils.";



        DownloadImageTask request = new DownloadImageTask(this, new Callback(){
            public void run(Bitmap result){

                //Enregistrement dans la base
                BO_Podcast nouveauPodcast1 = new BO_Podcast(namePodcast, descPodcast);

                mesPodcast.ajouterPodcast(nouveauPodcast1, UtilityImage.toBytes(result));
            }});
        request.execute(imagPodcast);


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
