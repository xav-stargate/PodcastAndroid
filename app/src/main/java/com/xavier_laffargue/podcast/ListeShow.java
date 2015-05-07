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


import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListeShow extends Activity {

    private PodcastDataSource mesPodcast;
    private ImageView iconePodcast;
    private TextView txt_description;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_show);

        Intent intent = getIntent();
        mesPodcast = new PodcastDataSource(this);
        mesPodcast.open();


        long idPodcast = intent.getLongExtra("idPodcast", 0);

        Podcast one = mesPodcast.getOnePodcast(idPodcast);


        iconePodcast = (ImageView)findViewById(R.id.icone_podcast_show);
        txt_description = (TextView)findViewById(R.id.txt_description_show);



        //Base de données SQLite


        Log.d("PODCASTXAVIER", Long.toString(one.getId()));
        setTitle(one.getNom());
        iconePodcast.setImageBitmap(UtilityImage.toBitmap(one.getImage()));
        txt_description.setText(one.getDescription());

        listView = (ListView) findViewById(R.id.list);

        /*

        ArrayAdapterPodcast adapter = new ArrayAdapterPodcast(this, mesPodcast.getAllPodcast());

        Log.d("PODCASTXAVIER", mesPodcast.getAllPodcast().get(1).getNom());
        listView.setAdapter(adapter);

getOnePodcast

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Podcast o = (Podcast)listView.getItemAtPosition(position);

                Intent intent = new Intent(ListePodcast.this,Podcast.class);
                //based on item add info to intent
                intent.putExtra("idPodcast", o.getId());
                startActivity(intent);

            }
        });*/



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
        startActivity(new Intent(this, AddPodcast.class));
    }

    public void openSettings() {
        startActivity(new Intent(this, SettingPodcast.class));
    }
}
