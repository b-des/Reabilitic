package com.reabilitic.ui.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.reabilitic.R;
import com.reabilitic.models.ArticleModel;
import com.reabilitic.ui.base.BaseActivity;
import com.reabilitic.ui.presenter.NewsViewPresenter;
import com.reabilitic.ui.views.MainActivity;
import com.reabilitic.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsViewActivity extends BaseActivity implements NewsViewContract.View{

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tvArticle)
    TextView tvArticle;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private String id;
    private NewsViewPresenter presenter;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_news_view);
        ButterKnife.bind(this);
        id =  getIntent().getIntExtra("id",0)+"";
        presenter = new NewsViewPresenter();
        presenter.attachView(this);

        init();
        presenter.viewIsReady();
    }

    @Override
    public void init(){
        //Utils.showLoading(this);
        String data = "<font color='white'>Vis wisi utinam nostro ex, no vim maluisset adversarium. Cum in dicam consul epicurei, sit ut tale facilisis. Ut vocent oblique nec, te errem possit inermis vix, ne solum mnesarchum sit. Est enim scaevola expetendis cu, vel an numquam vituperatoribus. An est virtute suscipit atomorum, partiendo erroribus nam eu. Qui in vide voluptatibus, per elit assum dissentias ea, movet scaevola omittantur eam te. Te omnes consulatu eam, te has putent gubergren mediocritatem, pri soleat phaedrum erroribus ne.\n" +
                "\n" +
                "Et mel velit persius probatus. Mei at utroque electram. Ea dico quando mucius vim, vel facilis periculis sententiae at, nam iriure rationibus ex. Labores copiosae mel id, at qui duis sensibus sententiae.\n</font>";
      // webView.setBackgroundResource(R.drawable.image_bg);

        webView.setBackgroundColor(Color.TRANSPARENT);


    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void showArticle(ArticleModel article) {
        pd = new ProgressDialog(this);
        pd.setCancelable(true);
        pd.setTitle(R.string.all_text_loading_dialog_title);
        pd.setMessage(getString(R.string.all_text_loading_dialog_desc));

        pd.show();
        tvTitle.setText(article.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvArticle.setText(Html.fromHtml(article.getFull_desc(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvArticle.setText(Html.fromHtml(article.getFull_desc()));
        }

        String style = "<style>body{color:#FFF;}</style>";
        webView.loadData(style+article.getFull_desc(),"text/html; charset=utf-8", "UTF-8");

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                pd.cancel();
                pd.dismiss();
            }
        });
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
