package com.reabilitic.ui.views;

import com.reabilitic.models.BookModel;
import com.reabilitic.models.FilmModel;
import com.reabilitic.models.GenreModel;
import com.reabilitic.ui.base.MvpPresenter;
import com.reabilitic.ui.base.MvpView;
import com.reabilitic.utils.Const;

import java.util.List;

public interface CatalogContract {
    interface View extends MvpView{
        void init();
        Const.SUBJECT_TYPE getSubjectType();
        GenreModel getGenre();
        void setUpFilms(List<FilmModel> films);
        void setUpBooks(List<BookModel> books);
        void showNotFoundAlert();
        void showNetworkErrorAlert();
    }

    interface Presenter extends MvpPresenter<View>{
        void onSearchButtonClick(String query);
    }
}
