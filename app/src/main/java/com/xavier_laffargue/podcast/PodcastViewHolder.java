package com.xavier_laffargue.podcast;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Xavier on 30/05/2015.
 */
public class PodcastViewHolder  extends RecyclerView.ViewHolder{
    public TextView id;
    public TextView nom;
    public ImageView img;
    public CardView cardView;

    public PodcastViewHolder(View itemView) {
        super(itemView);
        id = (TextView) itemView.findViewById(R.id.id_podcast_card);
        nom = (TextView) itemView.findViewById(R.id.nom_podcast_card);
        img = (ImageView) itemView.findViewById(R.id.image_podcast_card);
        cardView = (CardView) itemView.findViewById(R.id.cardview);
    }
}
