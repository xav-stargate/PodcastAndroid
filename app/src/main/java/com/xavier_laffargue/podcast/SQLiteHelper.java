package com.xavier_laffargue.podcast;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Xavier on 25/04/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_PODCAST = "podcast";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOM = "nom";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_DESCRIPTION = "description";


    public static final String TABLE_SHOW = "shows";
    public static final String COLUMN_ID_SHOW = "id";
    public static final String COLUMN_ID_PODCAST_SHOW = "id_podcast";
    public static final String COLUMN_NOM_SHOW = "nom";
    public static final String COLUMN_MP3_SHOW = "mp3";
    public static final String COLUMN_DESCRIPTION_SHOW = "description";


    private static final String DATABASE_NAME = "podcasts99546.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PODCAST + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NOM
            + " text not null, "+ COLUMN_IMAGE
            + " blob, "+ COLUMN_DESCRIPTION
            + " text); "
            + " create table "
            + TABLE_SHOW + "(" + COLUMN_ID_SHOW
            + " integer primary key autoincrement, " + COLUMN_ID_PODCAST_SHOW
            + " integer, " + COLUMN_NOM_SHOW
            + " text not null, "+ COLUMN_MP3_SHOW
            + " text, "+ COLUMN_DESCRIPTION_SHOW
            + " text);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PODCAST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOW);

        onCreate(db);
    }
}