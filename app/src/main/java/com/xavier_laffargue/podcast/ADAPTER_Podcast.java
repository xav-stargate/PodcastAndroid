package com.xavier_laffargue.podcast;

/**
 * Created by Xavier on 26/04/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ADAPTER_Podcast extends RecyclerView.Adapter<PodcastViewHolder> {

    private final List<BO_Podcast> mesPodcasts;
    private Context context;

    public ADAPTER_Podcast(Context context, List<BO_Podcast> _podcast) {

        this.mesPodcasts = _podcast;
        this.context = context;

    }

    @Override
    public PodcastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_podcast, parent, false);
        return new PodcastViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PodcastViewHolder holder, final int position) {
        final BO_Podcast podcast = mesPodcasts.get(position);

        holder.nom.setText(podcast.getNom());
        holder.id.setText(Long.toString(podcast.getId()));
        if(podcast.getImage() != null)
            holder.img.setImageBitmap(UtilityImage.toBitmap(podcast.getImage()));



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ACT_ListeShow.class);
                //based on item add info to intent
                Log.d(CONF_Application.NAME_LOG, " id podcast : " + Long.toString(podcast.getId()));
                intent.putExtra("idPodcast", podcast.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mesPodcasts.size();
    }

    /*-

    @Override
    public Object getItem(int location) {
        return mesPodcasts.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mesPodcasts.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //View rowView = inflater.inflate(R.layout.list_podcast, parent, false);


        View rowView = inflater.inflate(R.layout.card_podcast, parent, false);

        TextView nomPodcast = (TextView) rowView.findViewById(R.id.nom_podcast_card);
        TextView idPodcast = (TextView) rowView.findViewById(R.id.id_podcast_card);
        ImageView imagePodcast = (ImageView) rowView.findViewById(R.id.image_podcast_card);

        BO_Podcast pod = mesPodcasts.get(position);

        nomPodcast.setText(pod.getNom());
        idPodcast.setText(Long.toString(pod.getId()));


        if(pod.getImage() != null)
            imagePodcast.setImageBitmap(UtilityImage.toBitmap(pod.getImage()));

        return rowView;
    }
*/


}
