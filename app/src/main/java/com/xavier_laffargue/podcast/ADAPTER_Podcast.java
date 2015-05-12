package com.xavier_laffargue.podcast;

/**
 * Created by Xavier on 26/04/2015.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ADAPTER_Podcast extends BaseAdapter {
    private final Context context;
    private final List<BO_Podcast> mesPodcasts;

    public ADAPTER_Podcast(Context context, List<BO_Podcast> _podcast) {

        this.context = context;
        this.mesPodcasts = _podcast;

    }

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


        View rowView = inflater.inflate(R.layout.list_podcast, parent, false);

        TextView nomPodcast = (TextView) rowView.findViewById(R.id.nom_podcast);
        TextView idPodcast = (TextView) rowView.findViewById(R.id.id_podcast);
        ImageView imagePodcast = (ImageView) rowView.findViewById(R.id.image_podcast);

        BO_Podcast pod = mesPodcasts.get(position);

        nomPodcast.setText(pod.getNom());
        idPodcast.setText(Long.toString(pod.getId()));


        if(pod.getImage() != null)
            imagePodcast.setImageBitmap(UtilityImage.toBitmap(pod.getImage()));

        return rowView;
    }



}
