package com.reabilitic.ui.presenter;

import android.util.Log;

import com.reabilitic.App;
import com.reabilitic.models.ArticleModel;
import com.reabilitic.models.CategoryModel;
import com.reabilitic.ui.base.BasePresenter;
import com.reabilitic.ui.views.AboutContract;
import com.reabilitic.ui.views.TvListContract;
import com.reabilitic.utils.Const;
import com.reabilitic.utils.SharedPreferenceUtils;
import com.reabilitic.utils.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AboutPresenter extends BasePresenter<AboutContract.View> implements AboutContract.Presenter {
    private AboutContract.View view;
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void viewIsReady() {
        view = getView();
        view.init();
        loadNews();
    }

    @Override
    public void onItemClick(ArticleModel articleModel) {
        view.openArticle(articleModel.getId());
    }

    private void loadNews() {

        String hospital = SharedPreferenceUtils.getInstance(view.getContext()).getIntValue(Const.HOSPITAL, 0)+"";
        compositeDisposable.add(
                App.getApi().getArticles(hospital)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<ArticleModel>>() {

                            @Override
                            public void onSuccess(List<ArticleModel> articles) {
                                Log.d("LOG", "onSuccess: " + articles.toString());
                                Utils.hideLoading();
                                view.setUpArticlesList(articles);
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
