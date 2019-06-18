package com.reabilitic.ui.views;

import com.reabilitic.ui.base.MvpPresenter;
import com.reabilitic.ui.base.MvpView;

public interface DummyContract {
    interface View extends MvpView{
        void init();
    }

    interface Presenter extends MvpPresenter<View>{
    }
}
