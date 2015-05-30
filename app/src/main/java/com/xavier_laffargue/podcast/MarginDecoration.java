package com.xavier_laffargue.podcast;

/**
 * Created by Xavier on 30/05/2015.
 */
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MarginDecoration(Context context) {
        //margin = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
        margin = 4;
    }

    @Override
    public void getItemOffsets(
            Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
    }
}