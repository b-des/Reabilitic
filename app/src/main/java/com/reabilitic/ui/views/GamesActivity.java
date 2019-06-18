package com.reabilitic.ui.views;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.reabilitic.R;
import com.reabilitic.models.GameModel;
import com.reabilitic.ui.adapters.GamesAdapter;
import com.reabilitic.ui.base.BaseActivity;
import com.reabilitic.ui.presenter.GamesPresenter;
import com.reabilitic.utils.Factory;
import com.reabilitic.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GamesActivity extends BaseActivity implements GamesContract.View{

    @BindView(R.id.rvGames)
    RecyclerView rvGames;

    private GamesPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_games);
        ButterKnife.bind(this);
        presenter = new GamesPresenter();
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public void init() {

        GamesAdapter gamesAdapter = new GamesAdapter(this, Factory.initGames());
        rvGames.setAdapter(gamesAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 6);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        rvGames.setLayoutManager(layoutManager);
        gamesAdapter.setOnItemClickListener(item -> {
                presenter.onItemClick(item);
        });
    }

    @Override
    public void launchGame(String packageName) {
        PackageManager pm = getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage(packageName);
        if (launchIntent != null){
            startActivity(launchIntent);
        }else{
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
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
}
