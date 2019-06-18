package com.reabilitic.ui.views;

import android.content.Context;

import com.reabilitic.models.ArticleModel;
import com.reabilitic.ui.base.MvpPresenter;
import com.reabilitic.ui.base.MvpView;

import java.util.List;

public interface AboutContract {
    interface View extends MvpView{
        void init();
        void openArticle(int id);
        Context getContext();
        void setUpArticlesList(List<ArticleModel> articles);
    }

    interface Presenter extends MvpPresenter<View>{
        void onItemClick(ArticleModel articleModel);
    }
}
