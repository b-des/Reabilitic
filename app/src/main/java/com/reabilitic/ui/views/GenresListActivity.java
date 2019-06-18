package com.reabilitic.ui.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.reabilitic.R;
import com.reabilitic.models.GenreModel;
import com.reabilitic.ui.adapters.GenresAdapter;
import com.reabilitic.ui.base.BaseActivity;
import com.reabilitic.ui.presenter.GenresPresenter;
import com.reabilitic.utils.Const;
import com.reabilitic.utils.Factory;
import com.reabilitic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GenresListActivity extends BaseActivity implements GenresListContract.View{

    @BindView(R.id.rvGenres)
    RecyclerView rvGenres;

    private GenresAdapter genresAdapter;
    private GenresPresenter presenter;
    private Const.SUBJECT_TYPE SUBJECT_TYPE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_genres_list);
        ButterKnife.bind(this);
        SUBJECT_TYPE = (Const.SUBJECT_TYPE) getIntent().getSerializableExtra("type");
        presenter = new GenresPresenter();
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public void init() {
        Utils.showLoading(this);
    }

    @Override
    public void setUpGenres(List<GenreModel> genres) {
       // List<GenreModel> genres = SUBJECT_TYPE == Const.SUBJECT_TYPE.BOOKS ? Factory.initBookGenres() : Factory.initFilmGenres();
        genresAdapter = new GenresAdapter(this, genres, SUBJECT_TYPE);
        rvGenres.setAdapter(genresAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, Utils.calculateNoOfColumns(this,200));
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        rvGenres.setLayoutManager(layoutManager);

        genresAdapter.setOnItemClickListener(item -> {
            presenter.onGenreClick(item);
        });
    }

    @Override
    public Const.SUBJECT_TYPE getContentType() {
        return SUBJECT_TYPE;
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
    public void openCatalog(GenreModel genre) {
        Intent intent = new Intent(this, CatalogActivity.class);
        intent.putExtra("genre",genre);
        intent.putExtra("type",SUBJECT_TYPE);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
