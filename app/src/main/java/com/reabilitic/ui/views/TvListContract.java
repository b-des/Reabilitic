package com.reabilitic.ui.views;

import com.reabilitic.ui.base.MvpPresenter;
import com.reabilitic.ui.base.MvpView;

public interface TvListContract {
    interface View extends MvpView{
        void init();
        void startTvView(String chanel);
    }

    interface Presenter extends MvpPresenter<View>{
        void onTvChanelClick(String chanel);
    }
}
