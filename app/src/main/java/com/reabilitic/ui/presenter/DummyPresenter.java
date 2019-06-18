package com.reabilitic.ui.presenter;

import com.reabilitic.ui.base.BasePresenter;
import com.reabilitic.ui.views.CatalogContract;

public class DummyPresenter extends BasePresenter<CatalogContract.View> implements  CatalogContract.Presenter{
    CatalogContract.View view;
    @Override
    public void viewIsReady() {
        view = getView();
        view.init();
    }


    @Override
    public void onSearchButtonClick(String query) {

    }
}
