package com.reabilitic.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.reabilitic.models.CategoryModel;
import com.reabilitic.ui.base.MvpPresenter;
import com.reabilitic.ui.base.MvpView;

import java.util.List;

public interface MainContract {
    interface View extends MvpView{
        void init();
        void startApp(String packageName);
        void openActivity(final Class<? extends Activity> activity);
        void openActivityWithExtra(Intent intent);
        void openFirstTimeDialog(List<CategoryModel> hospitals);
        Context getContext();
        void showSkypeExplanation(String login, String password);
    }

    interface Presenter extends MvpPresenter<View>{
        void onSkypeIconClick();
        void onTvListIconClick();
        void onNewsIconClick();
        void onBooksIconClick();
        void onFilmsIconClick();
        void onInfoIconClick();
        void onGamesIconClick();
        void onControlIconClick();
        void onHospitalSelect(int hospital);
        void onCopySkypeLoginClick();
        void onCopySkypePasswordClick();
    }
}
