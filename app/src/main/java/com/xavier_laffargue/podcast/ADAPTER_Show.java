package com.xavier_laffargue.podcast;

/**
 * Created by Xavier on 26/04/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ADAPTER_Show extends BaseAdapter {
    private final Context context;
    private final List<BO_Show> mesShow;

    public ADAPTER_Show(Context context, ArrayList<BO_Show> _show) {

        this.context = context;
        this.mesShow = _show;

    }

    @Override
    public Object getItem(int location) {
        return mesShow.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mesShow.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View rowView = inflater.inflate(R.layout.list_podcast, parent, false);

        TextView nomPodcast = (TextView) rowView.findViewById(R.id.name_show);
        TextView idPodcast = (TextView) rowView.findViewById(R.id.id_show);

        BO_Show pod = mesShow.get(position);

        nomPodcast.setText(pod.getTitle());
        idPodcast.setText(Long.toString(pod.getIdShow()));


        return rowView;
    }



}
