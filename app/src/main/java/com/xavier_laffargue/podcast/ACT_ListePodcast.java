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



public class ACT_ListePodcast extends Activity {

    private PodcastDataSource mesPodcast;
    private ImageButton imgAddPodcast;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_podcast);

        imgAddPodcast = (ImageButton)findViewById(R.id.add_button_listPodcast);

        //Database
        mesPodcast = new PodcastDataSource(this);
        mesPodcast.open();


        listView = (ListView) findViewById(R.id.list);

        ADAPTER_Podcast adapter = new ADAPTER_Podcast(this, mesPodcast.getAllPodcast());
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                BO_Podcast o = (BO_Podcast) listView.getItemAtPosition(position);

                Intent intent = new Intent(ACT_ListePodcast.this, ACT_ListeShow.class);
                //based on item add info to intent
                Log.d(CONF_Application.NAME_LOG, " id podcast : " + Long.toString(o.getId()));
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
        startActivity(new Intent(this, ACT_AddPodcast.class));
    }

    public void openSettings() {
        startActivity(new Intent(this, ACT_SettingPodcast.class));
    }
}
