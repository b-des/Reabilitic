package com.reabilitic.ui.views;

import com.reabilitic.models.GenreModel;
import com.reabilitic.ui.base.MvpPresenter;
import com.reabilitic.ui.base.MvpView;
import com.reabilitic.utils.Const;

import java.util.List;

public interface GenresListContract {
    interface View extends MvpView{
        void init();
        void openCatalog(GenreModel genre);
        Const.SUBJECT_TYPE getContentType();
        void setUpGenres(List<GenreModel> genres);
    }

    interface Presenter extends MvpPresenter<View>{
        void onGenreClick(GenreModel genre);
    }
}
