package com.reabilitic.ui.presenter;

import com.reabilitic.ui.base.BasePresenter;
import com.reabilitic.ui.views.TvListContract;

public class TvListPresenter extends BasePresenter<TvListContract.View> implements  TvListContract.Presenter{
    TvListContract.View view;
    @Override
    public void viewIsReady() {
        view = getView();
        view.init();
    }

    @Override
    public void onTvChanelClick(String chanel) {
        view.startTvView(chanel);
    }
}
