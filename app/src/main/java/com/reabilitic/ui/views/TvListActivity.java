package com.reabilitic.ui.views;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.reabilitic.R;
import com.reabilitic.models.ChanelModel;
import com.reabilitic.ui.adapters.ChannelsAdapter;
import com.reabilitic.ui.base.BaseActivity;
import com.reabilitic.ui.presenter.TvListPresenter;
import com.reabilitic.utils.Factory;
import com.reabilitic.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TvListActivity extends BaseActivity implements TvListContract.View{


    @BindView(R.id.rvChanels)
    RecyclerView rvChanels;

    private TvListPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_tv_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ButterKnife.bind(this);
        presenter = new TvListPresenter();
        presenter.attachView(this);
        presenter.viewIsReady();
        init();
    }

    @Override
    public void init() {
        //Utils.showLoading(this);
        Observable.fromCallable(() -> {
            return Factory.initTvList();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {

                    ChannelsAdapter channelsAdapter = new ChannelsAdapter(this,result);
                    rvChanels.setAdapter(channelsAdapter);
                    rvChanels.setLayoutManager(new GridLayoutManager(this, 5));
                    channelsAdapter.setOnItemClickListener(item -> presenter.onTvChanelClick(item.getUrl()));
                });

        //ChannelsAdapter channelsAdapter = new ChannelsAdapter(this, Factory.initTvList());

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

    @OnClick(R.id.test)
    public void onTvChanelClick(){
        presenter.onTvChanelClick("http://live-rmg.cdnvideo.ru/rmg/rutv_hd.sdp/playlist.m3u8");
    }

    @Override
    public void startTvView(String chanel) {
        Intent intent = new Intent(this, TvViewActivity.class);
        intent.putExtra("stream",chanel);
        startActivity(intent);
    }
}
