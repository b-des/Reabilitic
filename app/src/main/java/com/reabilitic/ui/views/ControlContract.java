package com.reabilitic.ui.views;

import android.content.Context;

import com.reabilitic.ui.base.MvpPresenter;
import com.reabilitic.ui.base.MvpView;

public interface ControlContract {
    interface View extends MvpView{
        void init();
        String getMacAddress();
        String getIpAddress();
        void showLoading();
        void hideLoading();
        void saveIp(String ip);
        void showToast(int id);
        void showSnackBar(String mes);
        void log(String mes);
        void vibrate(int duration);
        Context getContext();
    }

    interface Presenter extends MvpPresenter<View>{
        void onButtonDown(String tag);
        void onButtonUp();
        void onMacScannedSuccess();
    }
}
