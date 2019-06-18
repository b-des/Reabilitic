package com.reabilitic.ui.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.reabilitic.R;
import com.reabilitic.models.BookModel;
import com.reabilitic.models.FilmModel;
import com.reabilitic.models.GenreModel;
import com.reabilitic.ui.adapters.BooksAdapter;
import com.reabilitic.ui.adapters.FilmsAdapter;
import com.reabilitic.ui.base.BaseActivity;
import com.reabilitic.ui.presenter.CatalogPresenter;
import com.reabilitic.utils.Const;
import com.reabilitic.utils.Factory;
import com.reabilitic.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class CatalogActivity extends BaseActivity implements CatalogContract.View {

    @BindView(R.id.rvBooks)
    RecyclerView rvBooks;

    @BindView(R.id.tvGenre)
    TextView tvGenre;


    @BindView(R.id.etSearchInput)
    EditText etSearchInput;

    private CatalogPresenter presenter;
    private Const.SUBJECT_TYPE SUBJECT_TYPE;
    private GenreModel genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);
        SUBJECT_TYPE = (Const.SUBJECT_TYPE) getIntent().getSerializableExtra("type");
        genre = (GenreModel) getIntent().getSerializableExtra("genre");
        presenter = new CatalogPresenter();
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public void init() {

        Utils.showLoading(this);
        switch (SUBJECT_TYPE) {
            case FILMS:

                break;
            case BOOKS:

                break;
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvBooks.setLayoutManager(layoutManager);

        tvGenre.setText(genre.getName());
        tvGenre.setPaintFlags(tvGenre.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick(R.id.navigationBack)
    public void onNavigationBackClick() {
        finish();
    }

    @OnClick(R.id.navigationMenu)
    public void onNavigationMenuClick() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.llSearchButton)
    void onSearchButtonClick() {
        String query = etSearchInput.getText().toString();
        if (!query.equals("")) {
            presenter.onSearchButtonClick(query);
            Utils.showLoading(this);
            hideKeyboard(this);
        }else{
            presenter.viewIsReady();
            hideKeyboard(this);
        }
    }


    @Override
    public void setUpFilms(List<FilmModel> films) {
        Utils.hideLoading();
        FilmsAdapter filmsAdapter = new FilmsAdapter(this, films);
        rvBooks.setAdapter(filmsAdapter);
        filmsAdapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(CatalogActivity.this, FilmActivity.class);
            intent.putExtra("film", item);
            startActivity(intent);
        });
    }

    @Override
    public void setUpBooks(List<BookModel> books) {
        Utils.hideLoading();
        BooksAdapter booksAdapter = new BooksAdapter(this, books);
        rvBooks.setAdapter(booksAdapter);
        booksAdapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(CatalogActivity.this, BookActivity.class);
            intent.putExtra("book", item);
            startActivity(intent);
        });
    }

    @Override
    public Const.SUBJECT_TYPE getSubjectType() {
        return SUBJECT_TYPE;
    }

    @Override
    public GenreModel getGenre() {
        return genre;
    }

    @Override
    public void showNotFoundAlert() {
        Utils.hideLoading();
        Toasty.warning(this, getString(R.string.catalog_text_not_found), Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void showNetworkErrorAlert() {
        Toasty.error(this, getString(R.string.all_text_network_error), Toast.LENGTH_SHORT, true).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
