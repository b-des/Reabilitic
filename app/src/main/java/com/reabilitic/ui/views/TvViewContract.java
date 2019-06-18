package com.reabilitic.ui.views;

import com.reabilitic.ui.base.MvpPresenter;
import com.reabilitic.ui.base.MvpView;

public interface TvViewContract {
    interface View extends MvpView{
        void init();
        void startTvView();
    }

    interface Presenter extends MvpPresenter<View>{
        void onTvChanelClick(String chanel);
    }
}
