package com.reabilitic.utils;

import com.reabilitic.R;
import com.reabilitic.m3u.M3UParser;
import com.reabilitic.m3u.uri.Uri;
import com.reabilitic.models.BookModel;
import com.reabilitic.models.ChanelModel;
import com.reabilitic.models.FilmModel;
import com.reabilitic.models.GameModel;
import com.reabilitic.models.GenreModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Factory {
    public static List<FilmModel> initFilms(){
        List<FilmModel> films = new ArrayList<>();
        films.add(new FilmModel());
        films.add(new FilmModel());
        films.add(new FilmModel());
        films.add(new FilmModel());
        films.add(new FilmModel());
        films.add(new FilmModel());
        films.add(new FilmModel());
        films.add(new FilmModel());
        films.add(new FilmModel());
        films.add(new FilmModel());
        films.add(new FilmModel());
        return films;
    }

    public static List<GenreModel> initFilmGenres(){
        List<GenreModel> genres = new ArrayList<>();
        /*genres.add(new GenreModel("Боевики","action_movies",R.drawable.action_films,"#a4917e"));
        genres.add(new GenreModel("Боевики","action_movies",R.drawable.action_films,"#a4917e"));
        genres.add(new GenreModel("Исторические","history",R.drawable.history_films,"#f39ca2"));
        genres.add(new GenreModel("Детективы","detectives",R.drawable.detectives_films,"#f9b256"));
        genres.add(new GenreModel("Военные","military",R.drawable.military_films,"#84a85e"));
        genres.add(new GenreModel("Триллеры","thrillers",R.drawable.thrillers_films,"#af8655"));
        genres.add(new GenreModel("Приключения","adventure",R.drawable.adventure_films,"#8cd568"));
        genres.add(new GenreModel("Аниме","anime",R.drawable.anime_films,"#25b4b1"));
        genres.add(new GenreModel("Драма","drama",R.drawable.drama_films,"#f78970"));
        genres.add(new GenreModel("Фантастика","fantastic",R.drawable.fantastic_films,"#16a085"));
        genres.add(new GenreModel("Комедия","comedy",R.drawable.comedy_films,"#ab8cc0"));
        genres.add(new GenreModel("Биография","biography",R.drawable.biography_films,"#72bee3"));
        genres.add(new GenreModel("Мультфильмы","cartoons",R.drawable.cartoons_films,"#ddd155"));
        genres.add(new GenreModel("Мелодрамы","melodramas",R.drawable.melodramas_films,"#f29fc5"));
        genres.add(new GenreModel("Ужасы и мистика","horror",R.drawable.horror_films,"#1abc9c"));
        genres.add(new GenreModel("Фэнтези","fantasy",R.drawable.fantasy_films,"#f28d4f"));
        genres.add(new GenreModel("Вестерны","western",R.drawable.western_films,"#b26459"));
        genres.add(new GenreModel("Мюзиклы","musicals",R.drawable.musicals_films,"#af8655"));
        genres.add(new GenreModel("Семейные","family",R.drawable.family_films,"#8cd568"));
        genres.add(new GenreModel("Спорт","sport",R.drawable.sport_films,"#25b4b1"));
        genres.add(new GenreModel("Арт-хаус","art_house",R.drawable.art_house_films,"#f78970"));*/
        return  genres;
    }

    public static List<BookModel> initBooks(){
        List<BookModel> books = new ArrayList<>();
        books.add(new BookModel());
        books.add(new BookModel());
        books.add(new BookModel());
        books.add(new BookModel());
        books.add(new BookModel());
        books.add(new BookModel());
        books.add(new BookModel());
        books.add(new BookModel());
        books.add(new BookModel());
        books.add(new BookModel());
        return books;
    }

    public static List<GenreModel> initBookGenres(){
        List<GenreModel> genres = new ArrayList<>();
       /* genres.add(new GenreModel("Боевики","action_movies",R.drawable.action_films,"#a4917e"));
        genres.add(new GenreModel("Боевики","action_movies",R.drawable.action_films,"#a4917e"));
        genres.add(new GenreModel("Классика","classic",R.drawable.history_films,"#f39ca2"));
        genres.add(new GenreModel("Детективы","detectives",R.drawable.detectives_films,"#f9b256"));
        genres.add(new GenreModel("Военные","military",R.drawable.military_films,"#84a85e"));
        genres.add(new GenreModel("Психология","psychology",R.drawable.psychology_films,"#af8655"));
        genres.add(new GenreModel("Приключение","adventure",R.drawable.adventure_films,"#8cd568"));
        genres.add(new GenreModel("Современная проза","modern_prose",R.drawable.modern_prose_films,"#25b4b1"));
        genres.add(new GenreModel("Драма","drama",R.drawable.drama_films,"#f78970"));
        genres.add(new GenreModel("Фантастика","fantastic",R.drawable.fantastic_films,"#16a085"));
        genres.add(new GenreModel("Юмор","humor",R.drawable.comedy_films,"#ab8cc0"));
        genres.add(new GenreModel("Политика","politics",R.drawable.politics_films,"#72bee3"));
        genres.add(new GenreModel("Сказки","fairy_tales",R.drawable.cartoons_films,"#ddd155"));
        genres.add(new GenreModel("Романы","novels",R.drawable.melodramas_films,"#f29fc5"));
        genres.add(new GenreModel("Ужасы и мистика","horror",R.drawable.horror_films,"#1abc9c"));
        genres.add(new GenreModel("Фэнтези","fantasy",R.drawable.fantasy_films,"#f28d4f"));
        genres.add(new GenreModel("Религия","religion",R.drawable.religion_films,"#b26459"));*/

        return  genres;
    }

    public static List<GameModel> initGames(){
        List<GameModel> games = new ArrayList<>();
        games.add(new GameModel("Шашки","com.dimcoms.checkers",R.drawable.checkers));
        games.add(new GameModel("Шашки","com.dimcoms.checkers",R.drawable.checkers));
        games.add(new GameModel("Шахматы","com.jetstartgames.chess",R.drawable.chess));
        games.add(new GameModel("Soul Knight","com.ChillyRoom.DungeonShooter",R.drawable.dungeon_shooter));
        games.add(new GameModel("Alto's Adventure","com.noodlecake.altosadventure",R.drawable.altosadventure));
        games.add(new GameModel("BADLAND","com.frogmind.badland",R.drawable.badland));
        games.add(new GameModel("Marble - Temple Quest","marble.temple.quest.legend",R.drawable.legend));
        games.add(new GameModel("Angry birds 2","com.rovio.baba",R.drawable.rovio_baba));
        games.add(new GameModel("Plants vs Zombies 2","com.ea.game.pvz2_row",R.drawable.pvz2_row));
        games.add(new GameModel("Plague Inc.","com.miniclip.plagueinc",R.drawable.plagueinc));
        games.add(new GameModel("Косынка","com.andreyrebrik.klondike",R.drawable.klondike));
        games.add(new GameModel("Кроссворды","com.appspot.orium_blog.crossword",R.drawable.orium_blog_crossword));
        games.add(new GameModel("Слова из слова","com.kabunov.wordsinaword",R.drawable.wordsinaword));
        games.add(new GameModel("Bouncemasters","com.playgendary.sportmasters",R.drawable.sportmasters));
        return games;
    }

    public static List<ChanelModel> initTvList(){
        List<ChanelModel> channels = new ArrayList<>();

        try {
            //Uri uri = Uri.parse("https://webhalpme.ru/iptvforever.m3u");
            Uri uri = Uri.parse("http://kombat-sb.ru/admin/api/get_chanels.php");
            M3UParser parser = new M3UParser();
            return parser.parse(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return channels;
    }
}
