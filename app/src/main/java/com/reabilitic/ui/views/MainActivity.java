package com.reabilitic.ui.views;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.adroitandroid.near.connect.NearConnect;
import com.adroitandroid.near.discovery.NearDiscovery;
import com.adroitandroid.near.model.Host;
import com.reabilitic.R;
import com.reabilitic.models.CategoryModel;
import com.reabilitic.ui.base.BaseActivity;
import com.reabilitic.ui.presenter.MainPresenter;
import com.stetcho.rxwifip2pmanager.data.wifi.RxWifiP2pManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends BaseActivity implements MainContract.View {

    private static final long DISCOVERABLE_TIMEOUT_MILLIS = 60000;
    private static final long DISCOVERY_TIMEOUT_MILLIS = 20000;
    private static final long DISCOVERABLE_PING_INTERVAL_MILLIS = 10000;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        preventStatusBarExpansion(this);

        ButterKnife.bind(this);
        presenter = new MainPresenter();
        presenter.attachView(this);
        presenter.viewIsReady();
    }


    @Override
    public void init() {
        Set<Host> peer = new HashSet<>();

        NearDiscovery mNearDiscovery = new NearDiscovery.Builder()
                .setContext(this)
                .setDiscoverableTimeoutMillis(DISCOVERABLE_TIMEOUT_MILLIS)
                .setDiscoveryTimeoutMillis(DISCOVERY_TIMEOUT_MILLIS)
                .setDiscoverablePingIntervalMillis(DISCOVERABLE_PING_INTERVAL_MILLIS)
                .setDiscoveryListener(getNearDiscoveryListener(), Looper.getMainLooper())
                .build();
        mNearDiscovery.getAllAvailablePeers();
        mNearDiscovery.startDiscovery();
       /* NearConnect mNearConnect = new NearConnect.Builder()
                .forPeers(peer) // Set<Host> peers
                .setContext(this)
                .setListener(getNearConnectListener(), Looper.getMainLooper())
                .build();*/
        // startActivity(new Intent(this, FilmViewActivity.class));

    }



    @NonNull
    private NearDiscovery.Listener getNearDiscoveryListener() {
        return new NearDiscovery.Listener() {
            @Override
            public void onPeersUpdate(Set<Host> hosts) {
                // Handle updates of peer list here - some peer might have got removed if it wasn't reachable anymore or some new peer might have been added
                Log.d("LOG_WIFI", "onPeersUpdate "+hosts.toString());
            }

            @Override
            public void onDiscoveryTimeout() {
                // This is called after the discovery timeout (specified in the builder) from starting discovery using the startDiscovery()
                Log.d("LOG_WIFI", "onDiscoveryTimeout");
            }

            @Override
            public void onDiscoveryFailure(Throwable e) {
                // This is called if discovery could not be started
                Log.d("LOG_WIFI", "onDiscoveryFailure");
                e.printStackTrace();
            }

            @Override
            public void onDiscoverableTimeout() {
                // This is called after the discoverable timeout (specified in the builder) from becoming discoverable by others using the makeDiscoverable()
                Log.d("LOG_WIFI", "onDiscoverableTimeout");
            }
        };
    }

    @NonNull
    private NearConnect.Listener getNearConnectListener() {
        return new NearConnect.Listener() {
            @Override
            public void onReceive(byte[] bytes, final Host sender) {
                // Process incoming data here
            }

            @Override
            public void onSendComplete(long jobId) {
                // jobId is the same as the return value of NearConnect.send(), an approximate epoch time of the send
            }

            @Override
            public void onSendFailure(Throwable e, long jobId) {
                // handle failed sends here
            }

            @Override
            public void onStartListenFailure(Throwable e) {
                // This tells that the NearConnect.startReceiving() didn't go through properly.
                // Common cause would be that another instance of NearConnect is already listening and it's NearConnect.stopReceiving() needs to be called first
            }
        };
    }

    @OnClick(R.id.ivSkypeLaunch)
    public void onSkypeIconClick() {
        presenter.onSkypeIconClick();
    }

    @OnClick(R.id.ivTvListLaunch)
    public void onTvIconClick() {
        presenter.onTvListIconClick();
    }

    @OnClick(R.id.ivNewsLaunch)
    public void onNewsIconClick() {
        presenter.onNewsIconClick();
    }

    @OnClick(R.id.ivBooksLaunch)
    public void onBooksIconClick() {
        presenter.onBooksIconClick();
    }

    @OnClick(R.id.ivFilmsLaunch)
    public void onFilmsIconClick() {
        presenter.onFilmsIconClick();
    }

    @OnClick(R.id.ivInfoLaunch)
    public void onInfoIconClick() {
        presenter.onInfoIconClick();
    }

    @OnClick(R.id.ivGamesLaunch)
    public void onGamesIconClick() {
        presenter.onGamesIconClick();
    }

    @OnClick(R.id.ivControlsLaunch)
    public void onControlIconClick() {
        presenter.onControlIconClick();
    }


    @Override
    public void startApp(String packageName) {
        PackageManager pm = getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        }
    }

    @Override
    public void openActivity(final Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }

    @Override
    public void openActivityWithExtra(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void openFirstTimeDialog(List<CategoryModel> hospitals) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflater = getLayoutInflater().inflate(R.layout.layout_dialog_first_start, null);
        // builder.setView(inflater);
        builder.setTitle(R.string.main_text_first_start_dialog_title);
        //builder.setMessage(R.string.main_text_first_start_dialog_message);
        builder.setCancelable(false);
        Observable.fromIterable(hospitals)
                .map(item -> item.getName())
                .toList()
                .subscribe(items -> {
                    final CharSequence[] Animals = items.toArray(new String[items.size()]);
                    builder.setItems(Animals, (dialog, which) -> {

                        presenter.onHospitalSelect(hospitals.get(which).getId());
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                });
    }

    @Override
    public void showSkypeExplanation(String login, String password) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_skype_account, null);
         builder.setView(view);
        view.findViewById(R.id.btnCopyLogin).setOnClickListener(v -> {
            presenter.onCopySkypeLoginClick();
        });
        view.findViewById(R.id.btnCopyPassword).setOnClickListener(v -> {
            presenter.onCopySkypePasswordClick();
        });
        builder.setNegativeButton(getString(R.string.all_text_cancel), (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setPositiveButton(getString(R.string.all_text_run), (dialog, which) -> {
            this.startApp("com.skype.raider");
        });
        builder.setCancelable(false);
        builder.show();
        TextView tvLogin = view.findViewById(R.id.tvLogin);
        TextView tvPassword = view.findViewById(R.id.tvPassword);
        tvLogin.setText(String.format(getString(R.string.main_text_skype_login),login));
        tvPassword.setText(String.format(getString(R.string.main_text_skype_password),password));
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * Overlay status bar
     *
     * @param context
     */
    public  void preventStatusBarExpansion(Context context) {
        WindowManager manager = ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE));

        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        //localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            localLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        //localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;

        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int result = 0;
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        } else {
            // Use Fallback size:
            result = 60; // 60px Fallback
        }

        localLayoutParams.height = result;
        localLayoutParams.format = PixelFormat.TRANSPARENT;

        CustomViewGroup view = new CustomViewGroup(MainActivity.this);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },1000);
       // manager.addView(view, localLayoutParams);
    }

    public static class CustomViewGroup extends ViewGroup {
        public CustomViewGroup(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            // Intercepted touch!
            return true;
        }
    }




    @Override
    public void onBackPressed() {

    }
}
