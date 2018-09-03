package com.parrotize.recordradiostream;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Player player;
    Recorder recorder;
    @BindView(R.id.editTextStreamURL) EditText editTextStreamURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        deleteRecordingFile();

        //recorder = new Recorder(this,"http://17733.live.streamtheworld.com:80/METRO_FM_SC","yayin.mp3");
        recorder = new Recorder(this,"http://uk7.internet-radio.com:8226/stream","yayin.mp3");
    }

    @OnClick(R.id.buttonPlay)
    public void onClickPlay(View view)
    {
        reCreateRecorder();

        Toast.makeText(this, recorder.getUrlPath(), Toast.LENGTH_SHORT).show();

        recorder.record();
        //player.play();
    }

    @OnClick(R.id.buttonPause)
    public void onClickPause(View view)
    {
        recorder.stopRecording();
        //player.stop();
        reCreateRecorder();
    }

    @OnClick(R.id.buttonPlayFromRecord)
    public void onClickPlayFromRecord(View view)
    {
        recorder.playFromRecording();

    }

    @OnClick(R.id.buttonStopFromRecord)
    public void onClickStopFromRecord(View view)
    {
        recorder.stopPlayingFromRecord();
    }

    public void deleteRecordingFile()
    {
        File file = new File(getCacheDir(),"yayin.mp3");
        if (file.exists())
        {
            file.delete();
        }
    }

    public void reCreateRecorder()
    {
        recorder.stopRecording();
        recorder.player=null;
        recorder = null;
        deleteRecordingFile();
        //System.gc();
        if (!editTextStreamURL.getText().toString().equals(""))
        {
            recorder = new Recorder(this,editTextStreamURL.getText().toString(),"yayin.mp3");
        }
        else
        {
            recorder = new Recorder(this,"http://uk7.internet-radio.com:8226/stream","yayin.mp3");
        }
    }

}
