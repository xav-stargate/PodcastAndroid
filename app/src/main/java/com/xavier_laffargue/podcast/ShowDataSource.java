package com.xavier_laffargue.podcast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Xavier on 25/04/2015.
 */
public class ShowDataSource {

    // Champs de la base de données
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_ID_PODCAST_SHOW,
            SQLiteHelper.COLUMN_MP3_SHOW,
            SQLiteHelper.COLUMN_NOM_SHOW,
            SQLiteHelper.COLUMN_DESCRIPTION_SHOW
    };

    public ShowDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void ajouterShows(ArrayList<BO_Show> listeShow) {


        for(final BO_Show unShow: listeShow)
        {
            ContentValues values = new ContentValues();
            values.put(SQLiteHelper.COLUMN_ID_PODCAST_SHOW, unShow.getIdPodcast());
            values.put(SQLiteHelper.COLUMN_MP3_SHOW, unShow.getMp3());
            values.put(SQLiteHelper.COLUMN_NOM_SHOW, unShow.getTitle());
            values.put(SQLiteHelper.COLUMN_DESCRIPTION, unShow.getDescription());


            Log.d(CONF_Application.NAME_LOG, " ADD SHOW " + unShow.getIdPodcast() + " title : " + unShow.getTitle());


            database.insert(SQLiteHelper.TABLE_SHOW, null, values);
        }
    }


    public void refreshShows(BO_Podcast nouveauPodcast) {

        Log.d(CONF_Application.NAME_LOG, " REFRESH PODCAST ID " + nouveauPodcast.getId());

        ArrayList<BO_Show> showRajoute = new ArrayList<>();



        for(final BO_Show nouveauShow: nouveauPodcast.getShows()) {
            if(!nouveauShow.existe(this.getAllShow(nouveauPodcast)))
            {
                Log.d(CONF_Application.NAME_LOG, " REFRESH PODCAST EXISTE PAS " + nouveauShow.getTitle());
                nouveauShow.setIdPodcast(nouveauPodcast.getId());
                showRajoute.add(nouveauShow);
            }
        }

        ajouterShows(showRajoute);
    }

    public void supprimerShow(BO_Show unShow) {
        long id = unShow.getIdShow();

        database.delete(SQLiteHelper.TABLE_SHOW, SQLiteHelper.COLUMN_ID_SHOW
                + " = " + id, null);
    }

    public BO_Show getOneShow(long id)
    {
        BO_Show newComment;
        String selectQuery = "SELECT * FROM "+SQLiteHelper.TABLE_SHOW+" WHERE id=?";
        Cursor c = database.rawQuery(selectQuery, new String[] { Long.toString(id) });
        if (c.moveToFirst()) {
            newComment = cursorToShow(c);
        } else {
            newComment = null;
        }
        c.close();


        return newComment;
    }

    public ArrayList<BO_Show> getAllShow(BO_Podcast _podcast) {
        ArrayList<BO_Show> comments = new ArrayList<>();

        Cursor cursor =  database.query(SQLiteHelper.TABLE_SHOW,
                allColumns, SQLiteHelper.COLUMN_ID_PODCAST_SHOW + " = " + _podcast.getId(), null,
                null, null, SQLiteHelper.COLUMN_ID + " ASC");


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BO_Show comment = cursorToShow(cursor);
            comments.add(comment);
            Log.d(CONF_Application.NAME_LOG, "OK : " + comment.getTitle());
            cursor.moveToNext();
        }

        cursor.close();
        return comments;
    }

    public ArrayList<HashMap<String, String>> getAllShowInHashMap(BO_Podcast _podcast) {

        ArrayList<HashMap<String, String>> liste = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_SHOW,
                allColumns, SQLiteHelper.COLUMN_ID_PODCAST_SHOW + " = " + _podcast.getId(), null,
                null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HashMap<String, String> map = new HashMap<>();


            map.put("id", Long.toString(cursorToShow(cursor).getIdShow()));
            map.put("nom", cursorToShow(cursor).getTitle());
            map.put("description", cursorToShow(cursor).getDescription());



            liste.add(map);
            cursor.moveToNext();
        }
        cursor.close();


        return liste;
    }
    private BO_Show cursorToShow(Cursor cursor) {
        BO_Show show = new BO_Show();
        show.setIdShow(cursor.getLong(0));
        show.setIdPodcast(cursor.getLong(1));
        show.setMp3(cursor.getString(2));
        show.setTitle(cursor.getString(3));
        show.setDescription(cursor.getString(4));

        return show;
    }
}

