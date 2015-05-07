package com.xavier_laffargue.podcast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.sql.Blob;
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
            SQLiteHelper.COLUMN_NOM, SQLiteHelper.COLUMN_IMAGE, SQLiteHelper.COLUMN_DESCRIPTION };

    public PodcastDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Podcast ajouterPodcast(Podcast nouveauPodcast, byte[] image) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_NOM, nouveauPodcast.getNom());
        values.put(SQLiteHelper.COLUMN_IMAGE, image);
        values.put(SQLiteHelper.COLUMN_DESCRIPTION, nouveauPodcast.getDescription());

        Log.d("PODCASTXAVIER", "Ajout d'un podcast : " + nouveauPodcast.getNom());


        long insertId = database.insert(SQLiteHelper.TABLE_PODCAST, null, values);

        Cursor cursor = database.query(SQLiteHelper.TABLE_PODCAST,
                allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Podcast newComment = cursorToPodcast(cursor);


        cursor.close();
        return newComment;
    }

    public void supprimerPodcast(Podcast monPodcast) {
        long id = monPodcast.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE_PODCAST, SQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public Podcast getOnePodcast(long id)
    {


        Podcast newComment;
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

    public List<Podcast> getAllPodcast() {
        List<Podcast> comments = new ArrayList<Podcast>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_PODCAST,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Podcast comment = cursorToPodcast(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
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
    private Podcast cursorToPodcast(Cursor cursor) {
        Podcast comment = new Podcast();
        comment.setId(cursor.getLong(0));
        comment.setNom(cursor.getString(1));
        comment.setImage(cursor.getBlob(2));
        comment.setDescription(cursor.getString(3));
        return comment;
    }
}
