package com.reabilitic.ui.presenter;

import android.util.Log;

import com.reabilitic.App;
import com.reabilitic.models.ArticleModel;
import com.reabilitic.ui.base.BasePresenter;
import com.reabilitic.ui.views.CatalogContract;
import com.reabilitic.ui.views.NewsViewContract;
import com.reabilitic.utils.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsViewPresenter extends BasePresenter<NewsViewContract.View> implements  NewsViewContract.Presenter{
    NewsViewContract.View view;
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void viewIsReady() {
        view = getView();
        view.init();
        loadArticle();
    }

    private void loadArticle(){
        String id = view.getId();
        Log.d("LOG", "ID: "+id);
        compositeDisposable.add(
                App.getApi().getArticle(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ArticleModel>() {
                            @Override
                            public void onSuccess(ArticleModel article) {
                                Log.d("LOG", "onSuccess: " + article.toString());
                                view.showArticle(article);

                            }

                            @Override
                            public void onError(Throwable e) {
                                Utils.hideLoading();
                                e.printStackTrace();
                            }
                        })
        );
    }
}
