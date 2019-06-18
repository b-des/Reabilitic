package com.reabilitic.ui.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.TextView;

import com.reabilitic.R;
import com.reabilitic.models.ArticleModel;
import com.reabilitic.ui.adapters.ArticlesAdapter;
import com.reabilitic.ui.base.BaseActivity;
import com.reabilitic.ui.presenter.AboutPresenter;
import com.reabilitic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity implements AboutContract.View{

    @BindView(R.id.rvArticles)
    RecyclerView rvArticles;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private AboutPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        presenter = new AboutPresenter();
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public void init() {
        Utils.showLoading(this);
        List<ArticleModel> articles = new ArrayList<>();

        tvTitle.setPaintFlags(tvTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

    }

    @Override
    public void setUpArticlesList(List<ArticleModel> articles) {
        ArticlesAdapter articlesAdapter = new ArticlesAdapter(this,articles);
        rvArticles.setAdapter(articlesAdapter);
        rvArticles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        articlesAdapter.setOnItemClickListener(item -> {
            presenter.onItemClick(item);
        });
    }

    @Override
    public void openArticle(int id) {
        Intent intent = new Intent(this, NewsViewActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
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
