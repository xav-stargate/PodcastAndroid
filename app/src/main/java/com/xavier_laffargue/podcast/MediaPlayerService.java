package com.xavier_laffargue.podcast;

/**
 * Created by Xavier on 30/05/2015.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSession.Callback;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import java.io.IOException;

public class MediaPlayerService extends Service {


    private MediaSessionManager m_objMediaSessionManager;
    private MediaSession m_objMediaSession;
    private MediaController m_objMediaController;
    private MediaPlayer m_objMediaPlayer;
    private Uri uri;
    private BO_Show myShow;
    private BO_Podcast myPodcast;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void handleIntent( Intent intent ) {

        Log.d(CONF_Application.NAME_LOG, "OKK handleIntent");
        if( intent == null || intent.getAction() == null )
            return;

        String action = intent.getAction();

        if( action.equalsIgnoreCase( CONF_Application.ACTION_PLAY ) ) {
            m_objMediaController.getTransportControls().play();
            m_objMediaPlayer.start();
        } else if( action.equalsIgnoreCase( CONF_Application.ACTION_PAUSE ) ) {
            //retreivePlaybackAction(1);
            m_objMediaPlayer.pause();
            m_objMediaController.getTransportControls().pause();
        } else if( action.equalsIgnoreCase( CONF_Application.ACTION_FAST_FORWARD ) ) {
            m_objMediaController.getTransportControls().fastForward();
        } else if( action.equalsIgnoreCase( CONF_Application.ACTION_REWIND ) ) {
            m_objMediaController.getTransportControls().rewind();
        } else if( action.equalsIgnoreCase( CONF_Application.ACTION_PREVIOUS ) ) {
            m_objMediaController.getTransportControls().skipToPrevious();
        } else if( action.equalsIgnoreCase(CONF_Application.ACTION_NEXT ) ) {
            m_objMediaController.getTransportControls().skipToNext();
        } else if( action.equalsIgnoreCase( CONF_Application.ACTION_STOP ) ) {
            m_objMediaController.getTransportControls().stop();
        }
    }

    private void buildNotification(Notification.Action action ) {

        Log.d(CONF_Application.NAME_LOG, "OKK buildNotification");
        Notification.MediaStyle style = new Notification.MediaStyle();
        style.setMediaSession(m_objMediaSession.getSessionToken());

        Intent intent = new Intent( getApplicationContext(), MediaPlayerService.class);
        intent.setAction( CONF_Application.ACTION_STOP );

        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        Notification.Builder builder = new Notification.Builder( this )
                .setSmallIcon(R.drawable.podcasts_app_white)
                .setLargeIcon(UtilityImage.toBitmap(myPodcast.getImage()))
                .setContentTitle(myShow.getMp3())
                .setContentText(myPodcast.getNom())
                .setDeleteIntent( pendingIntent )
                .setStyle(style);

       /* builder.addAction( generateAction( android.R.drawable.ic_media_previous, "Previous", CONF_Application.ACTION_PREVIOUS ) );
        builder.addAction( generateAction( android.R.drawable.ic_media_rew, "Rewind", CONF_Application.ACTION_REWIND ) );*/
        builder.addAction( action );
       /* builder.addAction( generateAction( android.R.drawable.ic_media_ff, "Fast Foward", CONF_Application.ACTION_FAST_FORWARD ) );
        builder.addAction( generateAction( android.R.drawable.ic_media_next, "Next", CONF_Application.ACTION_NEXT ) );*/

        //final TransportControls controls = m_objMediaSession.getController().getTransportControls();	
        NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        notificationManager.notify( 1, builder.build() );

    }

    private Notification.Action generateAction( int icon, String title, String intentAction ) {
        Log.d(CONF_Application.NAME_LOG, "OKK generateAction");
        Intent intent = new Intent( getApplicationContext(), MediaPlayerService.class );
        intent.setAction( intentAction );
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        return new Notification.Action.Builder( icon, title, pendingIntent ).build();

    }

    private PendingIntent retreivePlaybackAction(int which) {
        Intent action;
        PendingIntent pendingIntent;
        final ComponentName serviceName = new ComponentName(this, MediaPlayerService.class);
        switch (which) {
            case 1:

                // Play and pause
                action = new Intent(CONF_Application.ACTION_PLAY);
                action.setComponent(serviceName);
                pendingIntent = PendingIntent.getService(this, 1, action, 0);
                return pendingIntent;
            case 2:
                // Skip tracks
                action = new Intent(CONF_Application.ACTION_NEXT);
                action.setComponent(serviceName);
                pendingIntent = PendingIntent.getService(this, 2, action, 0);
                return pendingIntent;
            case 3:
                // Previous tracks
                action = new Intent(CONF_Application.ACTION_PREVIOUS);
                action.setComponent(serviceName);
                pendingIntent = PendingIntent.getService(this, 3, action, 0);
                return pendingIntent;
            case 4:
                //fast forward tracks
                action = new Intent(CONF_Application.ACTION_FAST_FORWARD);
                action.setComponent(serviceName);
                pendingIntent = PendingIntent.getService(this, 4, action, 0);
                return pendingIntent;
            case 5:
                //rewind tracks
                action = new Intent(CONF_Application.ACTION_REWIND);
                action.setComponent(serviceName);
                pendingIntent = PendingIntent.getService(this, 5, action, 0);
                return pendingIntent;
            default:
                break;
        }
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Long idShow = 0L;
        String mp3Show = null;
        if(intent != null) {
            mp3Show = intent.getStringExtra("mp3Show");
            idShow = intent.getLongExtra("idShow", 0);
        }

        if((m_objMediaSessionManager == null || myShow.getIdShow() != idShow) && idShow != 0) {

            if(myShow != null)
            {
                m_objMediaPlayer.stop();
            }

            //DataBase
            PodcastDataSource mesPodcast = new PodcastDataSource(this);
            mesPodcast.open();

            ShowDataSource mesShows = new ShowDataSource(this);
            mesShows.open();

            myShow = mesShows.getOneShow(idShow);
            myPodcast = mesPodcast.getOnePodcast(myShow.getIdPodcast());

            uri = Uri.parse(myShow.getTitle());

            uri = Uri.parse(mp3Show);

            Log.d(CONF_Application.NAME_LOG, "OKK 2");


            initMediaSessions();
        }
        Log.d(CONF_Application.NAME_LOG, "OKK 1");


        handleIntent( intent );
        return super.onStartCommand(intent, flags, startId);
    }

    private void initMediaSessions() {

        m_objMediaPlayer = new MediaPlayer();

        m_objMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        Log.d(CONF_Application.NAME_LOG, "OKK initMediaSessions");

        try {
            m_objMediaPlayer.setDataSource(this, uri);
        } catch (IllegalArgumentException e) {
            Log.d(CONF_Application.NAME_LOG,"You might not set the URI correctly!");
        } catch (SecurityException e) {
            Log.d(CONF_Application.NAME_LOG,"You might not set the URI correctly!");
        } catch (IllegalStateException e) {
            Log.d(CONF_Application.NAME_LOG,"You might not set the URI correctly!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            m_objMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            Log.d(CONF_Application.NAME_LOG,"You might not set the URI correctly!");
        } catch (IOException e) {
            Log.d(CONF_Application.NAME_LOG,"You might not set the URI correctly!");
        }

        m_objMediaPlayer.start();


        m_objMediaSessionManager = (MediaSessionManager) getSystemService(Context.MEDIA_SESSION_SERVICE);
        m_objMediaSession = new MediaSession(getApplicationContext(), "sample session");
        m_objMediaController = new MediaController(getApplicationContext(), m_objMediaSession.getSessionToken());
        m_objMediaSession.setActive(true);
        m_objMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        m_objMediaSession.setCallback(new Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                Log.d( CONF_Application.NAME_LOG, "onPlay");
                buildNotification( generateAction( android.R.drawable.ic_media_pause, "Pause", CONF_Application.ACTION_PAUSE ) );
            }

            @Override
            public void onPause() {
                super.onPause();
                Log.d(CONF_Application.NAME_LOG, "onPause");
                buildNotification(generateAction(android.R.drawable.ic_media_play, "Play", CONF_Application.ACTION_PLAY));
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
                Log.d(CONF_Application.NAME_LOG, "onSkipToNext");
                buildNotification( generateAction( android.R.drawable.ic_media_pause, "Pause", CONF_Application.ACTION_PAUSE ) );
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                Log.d(CONF_Application.NAME_LOG, "onSkipToPrevious");
                buildNotification( generateAction( android.R.drawable.ic_media_pause, "Pause", CONF_Application.ACTION_PAUSE ) );
            }

            @Override
            public void onFastForward() {
                super.onFastForward();
                Log.d(CONF_Application.NAME_LOG, "onFastForward");
            }

            @Override
            public void onRewind() {
                super.onRewind();
                Log.d(CONF_Application.NAME_LOG, "onRewind");
            }

            @Override
            public void onStop() {
                super.onStop();
                Log.d(CONF_Application.NAME_LOG, "onStop");
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel( 1 );
                Intent intent = new Intent( getApplicationContext(), MediaPlayerService.class );
                stopService( intent );
            }

            @Override
            public void onSeekTo(long pos) {
                super.onSeekTo(pos);
            }

            @Override
            public void onSetRating(Rating rating) {
                super.onSetRating(rating);
            }
        });

    }

    @Override
    public boolean onUnbind(Intent intent) {
        m_objMediaSession.release();
        return super.onUnbind(intent);
    }
}