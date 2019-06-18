package com.reabilitic.ui.presenter;

import android.util.Log;

import com.reabilitic.App;
import com.reabilitic.models.CategoryModel;
import com.reabilitic.models.GenreModel;
import com.reabilitic.ui.base.BasePresenter;
import com.reabilitic.ui.views.AboutContract;
import com.reabilitic.ui.views.GenresListContract;
import com.reabilitic.utils.Const;
import com.reabilitic.utils.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import rx.Subscriber;

public class GenresPresenter extends BasePresenter<GenresListContract.View> implements  GenresListContract.Presenter{
    GenresListContract.View view;
    private Const.SUBJECT_TYPE SUBJECT_TYPE;
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void viewIsReady() {
        view = getView();
        SUBJECT_TYPE = view.getContentType();
        view.init();
        loadGenres();
    }

    @Override
    public void onGenreClick(GenreModel genre) {
        view.openCatalog(genre);
    }

    private void loadGenres(){
        compositeDisposable.add(
                App.getApi().getCategories(SUBJECT_TYPE == Const.SUBJECT_TYPE.BOOKS ? "books_categories" : "films_categories")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<GenreModel>>(){

                            @Override
                            public void onSuccess(List<GenreModel> genres) {
                                Log.d("NETWORK", "onSuccess: "+genres.toString());

                                Utils.hideLoading();
                                view.setUpGenres(genres);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("NETWORK", "onError: "+e.getMessage());
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    public void destroy() {
        super.destroy();
        //compositeDisposable.dispose();
    }
}
