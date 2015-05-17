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


    public static final String TABLE_SHOW = "show";
    public static final String COLUMN_ID_SHOW = "id";
    public static final String COLUMN_ID_PODCAST_SHOW = "id_podcast";
    public static final String COLUMN_NOM_SHOW = "nom";
    public static final String COLUMN_MP3_SHOW = "mp3";
    public static final String COLUMN_DESCRIPTION_SHOW = "description";


    private static final String DATABASE_NAME = "podcasts895459.db";
    private static final int DATABASE_VERSION = 2;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE1 = "CREATE TABLE "
            + TABLE_SHOW + "(" + COLUMN_ID_SHOW
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ID_PODCAST_SHOW
            + " INTEGER, " + COLUMN_NOM_SHOW
            + " TEXT NOT NULL, "+ COLUMN_MP3_SHOW
            + " TEXT, "+ COLUMN_DESCRIPTION_SHOW
            + " TEXT); ";

    private static final String DATABASE_CREATE2 = "CREATE TABLE "
            + TABLE_PODCAST + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NOM
            + " TEXT NOT NULL, "+ COLUMN_IMAGE
            + " BLOB, "+ COLUMN_DESCRIPTION
            + " TEXT); ";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE1); Log.d(CONF_Application.NAME_LOG, "CREATION " + DATABASE_CREATE1);
        database.execSQL(DATABASE_CREATE2); Log.d(CONF_Application.NAME_LOG, "CREATION " + DATABASE_CREATE2);
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