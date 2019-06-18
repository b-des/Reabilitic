package com.reabilitic.ui.views;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reabilitic.FilmViewActivity;
import com.reabilitic.R;
import com.reabilitic.models.FilmModel;
import com.reabilitic.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilmActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvOriginalTitle)
    TextView tvOriginalTitle;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.tvGenre)
    TextView tvGenre;
    @BindView(R.id.tvYear)
    TextView tvYear;
    @BindView(R.id.tvDuration)
    TextView tvDuration;
    @BindView(R.id.tvDirector)
    TextView tvDirector;
    @BindView(R.id.tvActors)
    TextView tvActors;
    @BindView(R.id.tvRating)
    TextView tvRating;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.ivPoster)
    ImageView ivPoster;


    private FilmModel film;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_film);
        ButterKnife.bind(this);
        film = (FilmModel) getIntent().getSerializableExtra("film");
        init();
    }

    public void init(){
        tvTitle.setText(film.getNameRu());
        tvOriginalTitle.setText(film.getNameEn());
        tvCountry.setText(film.getCountry());
        tvGenre.setText(film.getCategory_name());
        tvYear.setText(film.getYear());
        tvDirector.setText(film.getDirected());
        tvActors.setText(film.getCasts());
        tvRating.setText(film.getRaiting());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDescription.setText(Html.fromHtml(film.getFull_desc(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDescription.setText(Html.fromHtml(film.getFull_desc()));
        }

        Glide.with(this)
                .load(film.getImage())
                .into(ivPoster);
        //tvDescription.setText(film.getFull_desc());
    }

    @OnClick(R.id.btnView)
    void onViewButtonClick(){
        Intent intent = new Intent(this, FilmViewActivity.class);
        //intent.putExtra("url","12X-GrTmMFY");
        intent.putExtra("url",film.getUrl());
        startActivity(intent);
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
