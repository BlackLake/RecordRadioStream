package com.parrotize.recordradiostream;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Player {

    private MediaPlayer mediaPlayer;
    Context context;
    String recordedFileName;

    public Player() {
    }

    public Player(Context context, String recordedFileName) {
        this.mediaPlayer = new MediaPlayer();
        this.context = context;
        this.recordedFileName = recordedFileName;
    }

    private void initializePlayer() throws IOException {

        File file = new File(context.getCacheDir(), recordedFileName);

        while (!file.exists());

        mediaPlayer.setDataSource(file.getPath());
        mediaPlayer.prepare();
    }


    public void play() {
        try {
            initializePlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.reset();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}
