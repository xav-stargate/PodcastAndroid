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
import android.widget.ImageView;
import android.widget.ListView;


import android.widget.TextView;

import java.util.ArrayList;


public class ACT_ListeShow extends Activity {

    private PodcastDataSource mesPodcast;
    private ShowDataSource mesShows;
    private ImageView iconePodcast;
    private TextView txt_description;
    private ListView listView;

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

        BO_Podcast one = mesPodcast.getOnePodcast(idPodcast);
        ArrayList<BO_Show> listeShow = one.getShows();


        iconePodcast = (ImageView)findViewById(R.id.icone_podcast_show);
        txt_description = (TextView)findViewById(R.id.txt_description_show);


        Log.d(CONF_Application.NAME_LOG, Long.toString(one.getId()));
        setTitle(one.getNom());
        iconePodcast.setImageBitmap(UtilityImage.toBitmap(one.getImage()));
        txt_description.setText(one.getDescription());



        listView = (ListView) findViewById(R.id.listView2);

        ADAPTER_Show adapter = new ADAPTER_Show(this, mesShows.getAllShow(one));
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                BO_Show o = (BO_Show) listView.getItemAtPosition(position);

                /*Intent intent = new Intent(ACT_ListePodcast.this, ACT_ListeShow.class);
                //based on item add info to intent
                Log.d(CONF_Application.NAME_LOG, " id podcast : " + Long.toString(o.getId()));
                intent.putExtra("idPodcast", o.getId());
                startActivity(intent);
                */
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
