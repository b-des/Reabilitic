package com.reabilitic.ui.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.reabilitic.App;
import com.reabilitic.models.ArticleModel;
import com.reabilitic.models.CategoryModel;
import com.reabilitic.ui.base.BasePresenter;
import com.reabilitic.ui.views.AboutActivity;
import com.reabilitic.ui.views.ControlActivity;
import com.reabilitic.ui.views.GamesActivity;
import com.reabilitic.ui.views.GenresListActivity;
import com.reabilitic.ui.views.MainActivity;
import com.reabilitic.ui.views.MainContract;
import com.reabilitic.ui.views.TvListActivity;
import com.reabilitic.utils.Const;
import com.reabilitic.utils.SharedPreferenceUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    MainContract.View view;
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String skypeLogin = "5436";
    private String skypePassword = "fgwhg";

    @Override
    public void viewIsReady() {
        view = getView();
        view.init();
        if (SharedPreferenceUtils.getInstance(view.getContext()).getBoolanValue(Const.IS_FIRST_START, true) ||
                SharedPreferenceUtils.getInstance(view.getContext()).getIntValue(Const.HOSPITAL, 0) == 0) {

            loadHospitals();
        }
    }

    @Override
    public void onSkypeIconClick() {
        view.startApp("com.skype.raider");
    }

    @Override
    public void onTvListIconClick() {
        view.openActivity(TvListActivity.class);
    }

    @Override
    public void onNewsIconClick() {
        view.startApp("ru.rian.reader");
    }

    @Override
    public void onBooksIconClick() {
        Intent intent = new Intent(view.getContext(), GenresListActivity.class);
        intent.putExtra("type", Const.SUBJECT_TYPE.BOOKS);
        view.openActivityWithExtra(intent);
    }

    @Override
    public void onFilmsIconClick() {
        Intent intent = new Intent(view.getContext(), GenresListActivity.class);
        intent.putExtra("type", Const.SUBJECT_TYPE.FILMS);
        view.openActivityWithExtra(intent);
    }

    @Override
    public void onInfoIconClick() {
        view.openActivity(AboutActivity.class);
    }

    @Override
    public void onGamesIconClick() {
        view.openActivity(GamesActivity.class);
    }

    @Override
    public void onControlIconClick() {
        view.openActivity(ControlActivity.class);
    }

    @Override
    public void onHospitalSelect(int hospital) {
        SharedPreferenceUtils.getInstance(view.getContext()).setValue(Const.IS_FIRST_START,false);
        SharedPreferenceUtils.getInstance(view.getContext()).setValue(Const.HOSPITAL,hospital);
    }

    @Override
    public void onCopySkypeLoginClick() {

    }

    @Override
    public void onCopySkypePasswordClick() {

    }


    void loadHospitals() {
        compositeDisposable.add(
                App.getApi().getAccounts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CategoryModel>>(){

                            @Override
                            public void onSuccess(List<CategoryModel> hospitals) {
                                Log.d("NETWORK", "onSuccess: "+hospitals.get(0).getName());
                                view.openFirstTimeDialog(hospitals);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );
    }

}
