package com.reabilitic.ui.views;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


import com.reabilitic.R;
import com.reabilitic.ui.base.BaseActivity;
import com.reabilitic.utils.CustomVideoView;
import com.reabilitic.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TvViewActivity extends BaseActivity {

    @BindView(R.id.btnPlayPause)
    FrameLayout btnPlayPause;

    private CustomVideoView videoView;
    private boolean isPlaying = true;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tv_view);
        ButterKnife.bind(this);
        init();
        String stream = getIntent().getStringExtra("stream");
        playTv(stream);
    }

    public void init(){
        videoView = findViewById(R.id.videoView);
        Utils.showLoading(this);
    }

    public void playTv(String stream){
        videoView.setVideoPath(stream);
        videoView.setOnPreparedListener(mp -> {
            videoView.start();
            Utils.hideLoading();
        });
        videoView.setOnErrorListener((mp, what, extra) -> {
            Log.d("LOG", "onError: ");
            Utils.hideLoading();
            Toast.makeText(TvViewActivity.this,getString(R.string.tv_cant_play_video),Toast.LENGTH_LONG).show();
            finish();
            return true;
        });
    }

    @OnClick(R.id.videoView)
    public void onVideoClick(){
        btnPlayPause.setVisibility(btnPlayPause.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        handler.postDelayed(() -> btnPlayPause.setVisibility(View.GONE),2000);
    }

    @OnClick(R.id.btnPlayPause)
    public void onPlayPauseButtonClick(){
        if(isPlaying){
            videoView.pause();
            isPlaying = false;
            btnPlayPause.setBackground(getDrawable(R.drawable.play_button));
        }else{
            videoView.start();
            isPlaying = true;
            btnPlayPause.setBackground(getDrawable(R.drawable.pause_button));
        }
    }
}
