package com.example.zinstream;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnInfoListener {

    ProgressBar progressBar;
    String path;
    VideoView mVideoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Vitamio.isInitialized(this);


        setContentView(R.layout.activity_main);

        playFunction();

    }
    void playFunction(){


        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mVideoView = (VideoView) findViewById(R.id.surface_view);

        //rtmp url
        path="rtmp://***.***.*.***:***/****/****";

        mVideoView.setVideoPath(path);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                }

            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                progressBar.setVisibility(View.GONE);
                break;
        }
        return true;
    }}