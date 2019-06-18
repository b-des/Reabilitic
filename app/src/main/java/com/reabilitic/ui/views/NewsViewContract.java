package com.reabilitic.ui.views;

import com.reabilitic.models.ArticleModel;
import com.reabilitic.ui.base.MvpPresenter;
import com.reabilitic.ui.base.MvpView;

public interface NewsViewContract {
    interface View extends MvpView{
        void init();
        String getId();
        void showArticle(ArticleModel article);
    }

    interface Presenter extends MvpPresenter<View>{
    }
}
