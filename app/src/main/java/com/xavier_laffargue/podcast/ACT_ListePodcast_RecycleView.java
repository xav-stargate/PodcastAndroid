package com.xavier_laffargue.podcast;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        loadPodcast();


        ImageButton imgAddPodcast = (ImageButton)findViewById(R.id.add_button_listPodcast);
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

    public void openAdd() {
        startActivity(new Intent(this, ACT_AddPodcast.class));
    }
}