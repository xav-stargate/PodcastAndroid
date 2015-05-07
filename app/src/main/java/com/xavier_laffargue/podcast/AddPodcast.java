package com.xavier_laffargue.podcast;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class AddPodcast extends Activity {

    private Button ajouter;
    Context c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_add_podcast);

        ajouter = (Button)findViewById(R.id.btn_ajouter_podcast);


        c = this;

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new DownloadImageTask(c, new Podcast("A ton âge", "durée : 00:08:47 - A ton age - par : Caroline Gilet - Anne et Pascal sont 'nés dans les pommes de terre', se sont mariés et on travaillé 28 ans côte à côte dans leur ferme de Lumigny. Aujourd'hui, la retraite approche et ils s'apprêtent à transmettre l'exploitation à un de leurs fils.")).execute("http://media.radiofrance-podcast.net/podcast09/RF_OMM_0000009471_ITE.jpg");
                new DownloadImageTask(c, new Podcast("L'interview politique de France Info", "Jean-François Achilli accueille la femme ou l'homme qui fait l'actualité du jour pour une interview incisive.")).execute("http://media.radiofrance-podcast.net/podcast09/RF_OMM_0000009420_ITE.jpg");
                new DownloadImageTask(c, new Podcast("Votre maison", "Retrouver François Sorel tous les Dimanhes dans l'émission avec Patrice")).execute("http://podcast.rmc.fr/images/1400_experts_maisonjpg_20150327152537.jpg");









            }
        });
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
        startActivity(new Intent(this, AddPodcast.class));
    }

    public void openSettings() {
        startActivity(new Intent(this, SettingPodcast.class));
    }
}
