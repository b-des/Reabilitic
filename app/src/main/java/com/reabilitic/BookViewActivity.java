package com.reabilitic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.reabilitic.models.BookModel;
import com.reabilitic.ui.base.BaseActivity;
import com.reabilitic.utils.SharedPreferenceUtils;
import com.reabilitic.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class BookViewActivity extends BaseActivity implements DownloadFile.Listener {

    private PDFPagerAdapter adapter;
    RemotePDFViewPager remotePDFViewPager;

    private BookModel book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_book_view);
        ButterKnife.bind(this);
        book = (BookModel) getIntent().getSerializableExtra("book");
        init();
    }

    public void init() {
        Utils.showLoading(this);
        remotePDFViewPager = new RemotePDFViewPager(this, book.getUrl(), this);



    }

    private void showContinueReadDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.book_text_continue_read_title);
        builder.setMessage(R.string.book_text_continue_read_description);
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.book_text_continue_read), (dialog, which) -> {
            remotePDFViewPager.setCurrentItem(SharedPreferenceUtils.getInstance(BookViewActivity.this).getIntValue(book.getName(),0));
        });

        builder.setNegativeButton(getString(R.string.book_text_start_new_read), (dialog, which) -> {
            SharedPreferenceUtils.getInstance(BookViewActivity.this).removeKey(book.getName());
            remotePDFViewPager.setCurrentItem(0);
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.close();
        }
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        setContentView(remotePDFViewPager);
        int savedPage = SharedPreferenceUtils.getInstance(BookViewActivity.this).getIntValue(book.getName(),0);
        if(savedPage > 0){
            showContinueReadDialog();
        }
        remotePDFViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                SharedPreferenceUtils.getInstance(BookViewActivity.this).setValue(book.getName(),remotePDFViewPager.getCurrentItem());
            }
        });
        Utils.hideLoading();
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
        Utils.hideLoading();
        Toast.makeText(this,getString(R.string.book_text_error_while_open_pdf),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }
}
