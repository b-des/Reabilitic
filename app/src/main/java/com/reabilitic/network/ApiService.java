package com.reabilitic.network;

import com.reabilitic.models.ArticleModel;
import com.reabilitic.models.BookModel;
import com.reabilitic.models.CategoryModel;
import com.reabilitic.models.FilmModel;
import com.reabilitic.models.GenreModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    @GET("?action=news_by_category")
    Single<List<ArticleModel>> getArticles(@Query("category") String category);

    @GET("?action=news_by_id")
    Single<ArticleModel> getArticle(@Query("id") String id);

    @GET("?action=accounts")
    Single<List<CategoryModel>> getAccounts();

    @GET("?action=books_by_id")
    Single<BookModel> getBookById(@Query("id") String id);

    @GET("?action=books_by_name")
    Single<List<BookModel>> getBooksByName(@Query("name") String name);

    @GET("?action=books_by_category")
    Single<List<BookModel>> getBooksByGenre(@Query("category") String category);

    @GET("?p=0")
    Single<List<GenreModel>> getCategories(@Query("action") String type);


    @GET("?action=films_by_id")
    Single<FilmModel> getFilmById(@Query("id") String id);

    @GET("?action=films_by_name")
    Single<List<FilmModel>> getFilmsByName(@Query("name") String name);

    @GET("?action=films_by_category")
    Single<List<FilmModel>> getFilmsByGenre(@Query("category") String category);

    @GET
    Single<String> sendControlCommand(@Url String url);

}
