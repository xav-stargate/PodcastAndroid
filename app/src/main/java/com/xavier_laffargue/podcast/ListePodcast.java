package com.xavier_laffargue.podcast;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;



public class ListePodcast extends Activity {

    private PodcastDataSource mesPodcast;
    private ImageButton imgAddPodcast;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_podcast);

        imgAddPodcast = (ImageButton)findViewById(R.id.add_button_listPodcast);

        //Base de données SQLite
        mesPodcast = new PodcastDataSource(this);
        mesPodcast.open();


        listView = (ListView) findViewById(R.id.list);
/*
        ListAdapter adapter = new SimpleAdapter(
                ListePodcast.this, mesPodcast.getAllInHashMap(),
                R.layout.list_podcast, new String[] { "id", "nom", "image" },
                new int[] { R.id.id_podcast, R.id.nom_podcast, R.id.image_podcast });
        // updating listview
*/



        ArrayAdapterPodcast adapter = new ArrayAdapterPodcast(this, mesPodcast.getAllPodcast());


        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Podcast o = (Podcast) listView.getItemAtPosition(position);

                Intent intent = new Intent(ListePodcast.this, ListeShow.class);
                //based on item add info to intent
                Log.d("PODCASTXAVIER id : ", Long.toString(o.getId()));
                intent.putExtra("idPodcast", o.getId());
                startActivity(intent);

            }
        });


        imgAddPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdd();
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
        startActivity(new Intent(this, AddPodcast.class));
    }

    public void openSettings() {
        startActivity(new Intent(this, SettingPodcast.class));
    }
}
