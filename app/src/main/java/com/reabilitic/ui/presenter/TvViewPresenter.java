package com.reabilitic.ui.presenter;

import com.reabilitic.ui.base.BasePresenter;
import com.reabilitic.ui.views.TvListContract;

public class TvViewPresenter extends BasePresenter<TvListContract.View> implements  TvListContract.Presenter{
    TvListContract.View view;
    @Override
    public void viewIsReady() {
        view = getView();
    }

    @Override
    public void onTvChanelClick(String chanel) {
        view.startTvView(chanel);
    }
}
