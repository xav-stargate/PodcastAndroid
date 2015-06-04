package com.xavier_laffargue.podcast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.melnykov.fab.FloatingActionButton;


/**
 * Created by Xavier on 30/05/2015.
 */

public class ACT_ListePodcast_RecycleView extends Activity {

    private PodcastDataSource mesPodcast;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_podcast);


        if(!CONF_Application.isNetworkConnected(getApplicationContext()))
        {
            AlertDialog alertDialog = new AlertDialog.Builder(ACT_ListePodcast_RecycleView.this).create();
            alertDialog.setTitle(getString(R.string.title_no_internet));
            alertDialog.setMessage(getString(R.string.error_no_internet));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.button_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.show();
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        loadPodcast();


        FloatingActionButton imgAddPodcast = (FloatingActionButton)findViewById(R.id.button_add_podcast);
        imgAddPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdd();
            }
        });


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadPodcast();
    }

    public void loadPodcast()
    {
        mesPodcast = new PodcastDataSource(this);
        mesPodcast.open();


        ADAPTER_Podcast adapter = new ADAPTER_Podcast(this, mesPodcast.getAllPodcast());

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                openAdd();
                return true;
           /* case R.id.action_settings:
                openSettings();
                return true;*/
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