package com.reabilitic.ui.presenter;

import com.reabilitic.models.GameModel;
import com.reabilitic.ui.base.BasePresenter;
import com.reabilitic.ui.views.CatalogContract;
import com.reabilitic.ui.views.GamesContract;

public class GamesPresenter extends BasePresenter<GamesContract.View> implements  GamesContract.Presenter{
    GamesContract.View view;
    @Override
    public void viewIsReady() {
        view = getView();
        view.init();
    }

    @Override
    public void onItemClick(GameModel gameModel) {
        view.launchGame(gameModel.getPackageName());
    }
}
