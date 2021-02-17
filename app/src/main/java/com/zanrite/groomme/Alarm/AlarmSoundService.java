package com.zanrite.groomme.Alarm;


/**
 * Created by parag on 13/09/17.
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.zanrite.groomme.R;


public class AlarmSoundService extends Service {
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Start media player
        mediaPlayer = MediaPlayer.create(this, R.raw.hollow);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);//set looping true to run it infinitely
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //On destory stop and release the media player
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }
}