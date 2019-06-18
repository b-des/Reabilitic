package com.reabilitic.ui.views;

import com.reabilitic.models.GameModel;
import com.reabilitic.ui.base.MvpPresenter;
import com.reabilitic.ui.base.MvpView;
import com.reabilitic.utils.Const;

public interface GamesContract {
    interface View extends MvpView{
        void init();
        void launchGame(String packageName);

    }

    interface Presenter extends MvpPresenter<View>{
        void onItemClick(GameModel gameModel);
    }
}
