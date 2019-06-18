package com.reabilitic.ui.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reabilitic.BookViewActivity;
import com.reabilitic.FilmViewActivity;
import com.reabilitic.R;
import com.reabilitic.models.BookModel;
import com.reabilitic.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookActivity extends BaseActivity {

    @BindView(R.id.tvPages)
    TextView tvPages;

    @BindView(R.id.tvAuthor)
    TextView tvAuthor;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvDescription)
    TextView tvDescription;

    @BindView(R.id.ivPoster)
    ImageView ivPoster;

    private BookModel bookModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        bookModel = (BookModel) getIntent().getSerializableExtra("book");
        init();

    }

    public void init(){
        tvPages.setText(String.format(getString(R.string.book_text_pages_quantity), bookModel.getPages()));
        tvAuthor.setText(bookModel.getAuthor());
        tvTitle.setText(bookModel.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDescription.setText(Html.fromHtml(bookModel.getFullDesc().equals("") ? bookModel.getShortDesc() : bookModel.getFullDesc(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDescription.setText(Html.fromHtml(bookModel.getFullDesc().equals("") ? bookModel.getShortDesc() : bookModel.getFullDesc()));
        }
        Glide.with(this).load(bookModel.getImage()).into(ivPoster);
    }

    @OnClick(R.id.btnRead)
    void onViewButtonClick(){
        Intent intent = new Intent(this, BookViewActivity.class);
        intent.putExtra("book",bookModel);
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
