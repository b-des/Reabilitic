package com.reabilitic.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.reabilitic.R;
import com.reabilitic.ui.adapters.GamesAdapter;
import com.reabilitic.ui.base.BaseActivity;
import com.reabilitic.ui.presenter.ControlPresenter;
import com.reabilitic.ui.presenter.GamesPresenter;
import com.reabilitic.utils.Const;
import com.reabilitic.utils.Factory;
import com.reabilitic.utils.SharedPreferenceUtils;
import com.reabilitic.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ControlActivity extends BaseActivity implements ControlContract.View, View.OnTouchListener{


    @BindView(R.id.btnScanQr)
    Button btnScanQr;

    @BindView(R.id.ivG1up)
    ImageView ivG1up;

    @BindView(R.id.ivG1down)
    ImageView ivG1down;

    @BindView(R.id.ivG2up)
    ImageView ivG2up;

    @BindView(R.id.ivG2down)
    ImageView ivG2down;

    @BindView(R.id.ivG3up)
    ImageView ivG3up;

    @BindView(R.id.ivG3down)
    ImageView ivG3down;

    @BindView(R.id.ivG4up)
    ImageView ivG4up;

    @BindView(R.id.ivG4down)
    ImageView ivG4down;

    @BindView(R.id.etConsole)
    EditText etConsole;

    private ControlPresenter presenter;
    private static final int QR_REQUEST = 1;
    private String mac = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_control);
        ButterKnife.bind(this);
        presenter = new ControlPresenter();
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void init() {
       //if(this.getMacAddress().equals("")){
           btnScanQr.setVisibility(View.VISIBLE);
       //}

        ivG1up.setOnTouchListener(this);
        ivG1down.setOnTouchListener(this);

        ivG2up.setOnTouchListener(this);
        ivG2down.setOnTouchListener(this);

        ivG3up.setOnTouchListener(this);
        ivG3down.setOnTouchListener(this);

        ivG4up.setOnTouchListener(this);
        ivG4down.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                String tag = v.getTag().toString();
                Log.d("LOG", tag);
                presenter.onButtonDown(tag);
                break;

            case MotionEvent.ACTION_UP:
                Log.d("LOG", "BUTTON RELEASED");
                presenter.onButtonUp();
                break;
        }
        return true;
    }

    @Override
    public void log(String mes) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etConsole.append(mes+"\r\n");
            }
        });

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getMacAddress() {
        return SharedPreferenceUtils.getInstance(this).getStringValue(Const.SP_ALIAS_MAC,"");
    }

    @Override
    public String getIpAddress() {
        return SharedPreferenceUtils.getInstance(this).getStringValue(Const.SP_ALIAS_IP,"");
    }

    @Override
    public void saveIp(String ip) {
        SharedPreferenceUtils.getInstance(this).setValue(Const.SP_ALIAS_IP,ip);
    }

    @OnClick(R.id.btnScanQr)
    public void onScanQrClick(){
        startActivity(new Intent(this,WifiScannerActivity.class));
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> Utils.showLoading(ControlActivity.this));

    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> Utils.hideLoading());

    }

    @Override
    public void showSnackBar(String mes) {
        runOnUiThread(() -> Snackbar.make(findViewById(android.R.id.content),mes,Snackbar.LENGTH_LONG).show());

    }

    @Override
    public void vibrate(int duration) {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(duration);
        }
    }

    @OnClick(R.id.navigationBack)
    public void onNavigationBackClick(){
        finish();
    }

    @OnClick(R.id.navigationMenu)
    public void onNavigationMenuClick(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void showToast(int id) {
        runOnUiThread(() -> Toast.makeText(ControlActivity.this,getString(id),Toast.LENGTH_LONG).show());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case QR_REQUEST:
                    String mac = data.getStringExtra("mac");
                    btnScanQr.setVisibility(View.GONE);
                    this.mac = mac;
                    presenter.onMacScannedSuccess();
                    //SharedPreferenceUtils.getInstance(this).setValue(Const.SP_ALIAS_MAC,mac);
                    break;
            }
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
