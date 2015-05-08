package com.xavier_laffargue.podcast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Xavier on 25/04/2015.
 */
public class PodcastDataSource {

    // Champs de la base de données
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_NOM,
            SQLiteHelper.COLUMN_IMAGE,
            SQLiteHelper.COLUMN_DESCRIPTION
    };

    public PodcastDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public BO_Podcast ajouterPodcast(BO_Podcast nouveauPodcast, byte[] image) {
        ContentValues values = new ContentValues();

        values.put(SQLiteHelper.COLUMN_NOM, nouveauPodcast.getNom());
        values.put(SQLiteHelper.COLUMN_IMAGE, image);
        values.put(SQLiteHelper.COLUMN_DESCRIPTION, nouveauPodcast.getDescription());

        Log.d(CONF_Application.NAME_LOG, " ADD PODCAST " + nouveauPodcast.getNom());


        long insertId = database.insert(SQLiteHelper.TABLE_PODCAST, null, values);

        Cursor cursor = database.query(SQLiteHelper.TABLE_PODCAST,
                allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        BO_Podcast newComment = cursorToPodcast(cursor);


        cursor.close();
        return newComment;
    }

    public void supprimerPodcast(BO_Podcast monPodcast) {
        long id = monPodcast.getId();

        database.delete(SQLiteHelper.TABLE_PODCAST, SQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public BO_Podcast getOnePodcast(long id)
    {


        BO_Podcast newComment;
        String selectQuery = "SELECT * FROM "+SQLiteHelper.TABLE_PODCAST+" WHERE id=?";
        Cursor c = database.rawQuery(selectQuery, new String[] { Long.toString(id) });
        if (c.moveToFirst()) {
            newComment = cursorToPodcast(c);
        } else {
            newComment = null;
        }
        c.close();


        return newComment;
    }

    public List<BO_Podcast> getAllPodcast() {
        List<BO_Podcast> comments = new ArrayList<BO_Podcast>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_PODCAST,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BO_Podcast comment = cursorToPodcast(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        return comments;
    }

    public ArrayList<HashMap<String, String>> getAllInHashMap() {

        ArrayList<HashMap<String, String>> liste = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_PODCAST, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HashMap<String, String> map = new HashMap<>();

            // adding each child node to HashMap key => value
            map.put("id", Long.toString(cursorToPodcast(cursor).getId()));
            map.put("nom", cursorToPodcast(cursor).getNom());
            map.put("image", cursorToPodcast(cursor).getImage().toString());
            map.put("description", cursorToPodcast(cursor).getDescription());



            liste.add(map);
            cursor.moveToNext();
        }
        cursor.close();


        return liste;
    }
    private BO_Podcast cursorToPodcast(Cursor cursor) {
        BO_Podcast comment = new BO_Podcast();
        comment.setId(cursor.getLong(0));
        comment.setNom(cursor.getString(1));
        comment.setImage(cursor.getBlob(2));
        comment.setDescription(cursor.getString(3));
        return comment;
    }
}
