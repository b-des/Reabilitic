package com.reabilitic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.reabilitic.ui.base.BaseActivity;

public class FilmViewActivity extends BaseActivity {
    YouTubePlayerView youtubePlayerView;
    private String videoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_film_view);
        videoId = getIntent().getStringExtra("url");
        if(videoId.startsWith("https://")){
            String parts[] = videoId.split("=");
            videoId = parts[1];
        }
        init();
    }

    public void init() {
        youtubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youtubePlayerView);
        youtubePlayerView.enterFullScreen();
        youtubePlayerView.isFullScreen();
        youtubePlayerView.getPlayerUIController().showFullscreenButton(false);
        youtubePlayerView.getPlayerUIController().showYouTubeButton(false);
        youtubePlayerView.getPlayerUIController().showCustomAction1(false);
        youtubePlayerView.getPlayerUIController().showCustomAction2(false);
        youtubePlayerView.getPlayerUIController().showMenuButton(false);
        youtubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer youTubePlayer) {
                youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        super.onReady();
                        youTubePlayer.loadVideo(videoId, 0);
                        youtubePlayerView.enterFullScreen();
                    }
                });
            }


        }, true);
    }

}
