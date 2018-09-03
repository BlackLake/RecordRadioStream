package com.parrotize.recordradiostream;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Recorder extends AsyncTask {

    private Context context;
    private String urlPath;
    private String recordedFileName;
    private boolean isRecording = false;
    private MediaPlayer mediaPlayer;

    Player player;

    public Recorder() {
    }

    public Recorder(Context context, String url, String recordedFilePath) {
        this.context = context;
        this.urlPath = url;
        this.recordedFileName = recordedFilePath;
        this.mediaPlayer = new MediaPlayer();
        player = new Player(context,recordedFileName);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        isRecording = true;

        try {
            URL url = new URL(urlPath);
            InputStream inputStream = url.openStream();

            File file = new File(context.getCacheDir(), recordedFileName);
            OutputStream outputStream = new FileOutputStream(file);

           // byte[] buffer = new byte[1];
            int read;

            while ((read = inputStream.read()) != -1) {
                //if (!isRecording) break;
                if(isCancelled())
                    break;
                outputStream.write(read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public void record()
    {
        File file = new File(context.getCacheDir(),recordedFileName);
        if (file.exists())
        {
            file.delete();
        }

        this.execute();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                player.play();
            }

        }, 3000);

    }

    public void stopRecording()
    {
        isRecording=false;
        this.cancel(true);
        player.stop();
        player.getMediaPlayer().reset();
    }

    public void playFromRecording()
    {
        try {
            File file = new File(context.getCacheDir(), recordedFileName);
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPlayingFromRecord()
    {
        mediaPlayer.stop();
        mediaPlayer.reset();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getRecordedFileName() {
        return recordedFileName;
    }

    public void setRecordedFileName(String recordedFileName) {
        this.recordedFileName = recordedFileName;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void setRecording(boolean recording) {
        this.isRecording = recording;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

}
