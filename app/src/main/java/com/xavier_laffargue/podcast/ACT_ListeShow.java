package com.xavier_laffargue.podcast;

/**
 * Created by Xavier on 26/04/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;


import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ACT_ListeShow extends Activity {

    private PodcastDataSource mesPodcast;
    private ShowDataSource mesShows;
    private ImageView iconePodcast;
    private TextView txt_description;
    private ListView listView;
    private ImageButton buttonRefresh;
    private BO_Podcast monPodcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_show);

        Intent intent = getIntent();

        //DataBase
        mesPodcast = new PodcastDataSource(this);
        mesPodcast.open();

        mesShows = new ShowDataSource(this);
        mesShows.open();



        long idPodcast = intent.getLongExtra("idPodcast", 0);

        monPodcast = mesPodcast.getOnePodcast(idPodcast);
        ArrayList<BO_Show> listeShow = monPodcast.getShows();


        iconePodcast = (ImageView)findViewById(R.id.icone_podcast_show);
        txt_description = (TextView)findViewById(R.id.txt_description_show);
        buttonRefresh = (ImageButton) findViewById(R.id.button_refresh_show);

        Log.d(CONF_Application.NAME_LOG, Long.toString(monPodcast.getId()));
        setTitle(monPodcast.getNom());
        iconePodcast.setImageBitmap(UtilityImage.toBitmap(monPodcast.getImage()));
        txt_description.setText(monPodcast.getDescription());



        listView = (ListView) findViewById(R.id.listView2);

        ADAPTER_Show adapter = new ADAPTER_Show(this, mesShows.getAllShow(monPodcast));
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                BO_Show o = (BO_Show) listView.getItemAtPosition(position);

                Intent intent = new Intent(ACT_ListeShow.this, ACT_Player.class);
                //based on item add info to intent
                Log.d(CONF_Application.NAME_LOG, " id show : " + o.getMp3());
                intent.putExtra("idShow", o.getIdShow());
                startActivity(intent);

            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*
                DownloadReadXmlTask pod = new DownloadReadXmlTask(this, new Callback(){
                    public void run(Object result){
                        //Enregistrement dans la base

                        podcastDownloaded = (BO_Podcast)result;

                        mesPodcast.ajouterPodcast(podcastDownloaded, mesShows);

                    }});
                pod.execute(monPodcast.getUrlXML());*/
                Toast.makeText(getApplicationContext(), "XML : " + monPodcast.getUrlXML(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_liste_podcast, menu);
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
