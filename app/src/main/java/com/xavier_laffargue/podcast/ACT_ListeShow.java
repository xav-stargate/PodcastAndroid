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
import android.widget.ImageView;
import android.widget.ListView;


import android.widget.TextView;


public class ACT_ListeShow extends Activity {

    private PodcastDataSource mesPodcast;
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


        long idPodcast = intent.getLongExtra("idPodcast", 0);

        BO_Podcast one = mesPodcast.getOnePodcast(idPodcast);


        iconePodcast = (ImageView)findViewById(R.id.icone_podcast_show);
        txt_description = (TextView)findViewById(R.id.txt_description_show);





        Log.d(CONF_Application.NAME_LOG, Long.toString(one.getId()));
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
        startActivity(new Intent(this, ACT_AddPodcast.class));
    }

    public void openSettings() {
        startActivity(new Intent(this, ACT_SettingPodcast.class));
    }
}
