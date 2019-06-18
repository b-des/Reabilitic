package com.reabilitic.ui.presenter;

import android.util.Log;

import com.reabilitic.App;
import com.reabilitic.models.ArticleModel;
import com.reabilitic.models.BookModel;
import com.reabilitic.models.FilmModel;
import com.reabilitic.models.GenreModel;
import com.reabilitic.ui.base.BasePresenter;
import com.reabilitic.ui.views.CatalogContract;
import com.reabilitic.utils.Const;

import java.util.List;
import java.util.Queue;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CatalogPresenter extends BasePresenter<CatalogContract.View> implements  CatalogContract.Presenter{
    CatalogContract.View view;
    private Const.SUBJECT_TYPE SUBJECT_TYPE;
    private GenreModel genre;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void viewIsReady() {
        view = getView();
        view.init();
        SUBJECT_TYPE = view.getSubjectType();
        genre = view.getGenre();
        switch (SUBJECT_TYPE){
            case BOOKS:
                loadBooks();
                break;
            case FILMS:
                loadFilms();
                break;
        }
    }

    @Override
    public void onSearchButtonClick(String query) {
        switch (SUBJECT_TYPE){
            case BOOKS:
                searchBooks(query);
                break;
            case FILMS:
                searchFilms(query);
                break;
        }
    }

    private void loadFilms(){
        compositeDisposable.add(
                App.getApi().getFilmsByGenre(genre.getTranslit())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<FilmModel>>() {
                            @Override
                            public void onSuccess(List<FilmModel> response) {
                                Log.d("NETWORK", "onSuccess: " + response.toString());
                                view.setUpFilms(response);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("NETWORK", "onError: " + e.getMessage());
                                view.showNetworkErrorAlert();
                            }
                        })
        );
    }

    private void loadBooks(){
        compositeDisposable.add(
                App.getApi().getBooksByGenre(genre.getTranslit())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<BookModel>>() {
                            @Override
                            public void onSuccess(List<BookModel> response) {
                                Log.d("NETWORK", "onSuccess: " + response.toString());
                                view.setUpBooks(response);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("NETWORK", "onError: " + e.getMessage());
                                view.showNetworkErrorAlert();
                            }
                        })
        );
    }


    private void searchFilms(String query){
        compositeDisposable.add(
                App.getApi().getFilmsByName(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<FilmModel>>() {
                            @Override
                            public void onSuccess(List<FilmModel> response) {
                                Log.d("NETWORK", "onSuccess search films: " + response.toString());
                                if(response.size() == 0){
                                    view.showNotFoundAlert();
                                }else{
                                    view.setUpFilms(response);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("NETWORK", "onError: " + e.getMessage());
                                view.showNetworkErrorAlert();
                            }
                        })
        );
    }

    private void searchBooks(String query){
        compositeDisposable.add(
                App.getApi().getBooksByName(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<BookModel>>() {
                            @Override
                            public void onSuccess(List<BookModel> response) {
                                Log.d("NETWORK", "onSuccess: " + response.toString());
                                if(response.size() == 0){
                                    view.showNotFoundAlert();
                                }else{
                                    view.setUpBooks(response);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("NETWORK", "onError: " + e.getMessage());
                                view.showNetworkErrorAlert();
                            }
                        })
        );
    }

    @Override
    public void destroy() {
        super.destroy();
        compositeDisposable.dispose();
    }
}
