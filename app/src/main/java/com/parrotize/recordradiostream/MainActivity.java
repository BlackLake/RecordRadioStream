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

    Recorder recorder;
    String streamURL = "http://159.253.37.137:9914/";
    String recordedFileName = "yayin.mp3";
    @BindView(R.id.editTextStreamURL)
    EditText editTextStreamURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        deleteRecordingFile();

    }

    @OnClick(R.id.buttonPlay)
    public void onClickPlay(View view) {

        reCreateRecorder();
        deleteRecordingFile();

        Toast.makeText(this, recorder.getUrlPath(), Toast.LENGTH_SHORT).show();

        recorder.record();
    }

    @OnClick(R.id.buttonPause)
    public void onClickPause(View view) {
        recorder.stopRecording();
        reCreateRecorder();
    }

    @OnClick(R.id.buttonPlayFromRecord)
    public void onClickPlayFromRecord(View view) {
        recorder.playFromRecording();

    }

    @OnClick(R.id.buttonStopFromRecord)
    public void onClickStopFromRecord(View view) {
        recorder.stopPlayingFromRecord();
    }

    public void deleteRecordingFile() {
        File file = new File(getCacheDir(), "yayin.mp3");
        if (file.exists()) {
            file.delete();
        }
    }

    public void reCreateRecorder() {
        if (recorder != null) {
            if (recorder.isRecording()) recorder.stopRecording();
            recorder.player = null;
            recorder = null;
        }
        if (!editTextStreamURL.getText().toString().equals("")){
            streamURL = editTextStreamURL.getText().toString();
        }
        else
        {
            streamURL = "http://159.253.37.137:9914/";
        }
        recorder = new Recorder(this, streamURL, recordedFileName);
    }

}
