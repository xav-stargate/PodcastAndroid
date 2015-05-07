package com.xavier_laffargue.podcast;

/**
 * Created by Xavier on 26/04/2015.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ArrayAdapterPodcast extends BaseAdapter {
    private final Context context;
    private final List<Podcast> mesPodcasts;

    public ArrayAdapterPodcast(Context context, List<Podcast> _podcast) {

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

        Podcast pod = mesPodcasts.get(position);
        Log.d("PODCASTXAVIER id", Integer.toString(position));

        nomPodcast.setText(pod.getNom());
        idPodcast.setText(Long.toString(pod.getId()));

        imagePodcast.setImageBitmap(UtilityImage.toBitmap(pod.getImage()));

        return rowView;
    }



}
