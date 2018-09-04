package com.parrotize.recordradiostream;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class Player  {

    // 1. Create a default TrackSelector
    Handler mainHandler;
    BandwidthMeter bandwidthMeter;
    TrackSelection.Factory audioTrackSelectionFactory;
    DefaultTrackSelector trackSelector;

    // 2. Create the player
    SimpleExoPlayer player;

    // Measures bandwidth during playback. Can be null if not required.
    DefaultBandwidthMeter defaultBandwidthMeter;
    // Produces DataSource instances through which media data is loaded.
    DataSource.Factory dataSourceFactory;
    // This is the MediaSource representing the media to be played.
    MediaSource audioSource;

    Context context;
    String recordedFileName;
    String urlPath;

    public Player() {
    }

    public Player(Context context, String recordedFileName, String urlPath) {
        this.context = context;
        this.recordedFileName = recordedFileName;
        this.urlPath = urlPath;


        // 1. Create a default TrackSelector
        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();
        audioTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(audioTrackSelectionFactory);

        // 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);


        // Measures bandwidth during playback. Can be null if not required.
        bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "yourApplicationName"), defaultBandwidthMeter);
        // This is the MediaSource representing the media to be played.
        audioSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(urlPath));

    }


    public void play() {
        // Prepare the player with the source.
        player.prepare(audioSource);
        player.setPlayWhenReady(true);
    }

    public void stop() {

        player.stop();
        player.release();

    }


}
