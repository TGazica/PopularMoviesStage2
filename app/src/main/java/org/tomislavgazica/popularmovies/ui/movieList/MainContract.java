package org.tomislavgazica.popularmovies.ui.movieList;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.tomislavgazica.popularmovies.model.Movie;

import java.util.List;

public interface MainContract {

    interface View {

        void setMovieList(List<Movie> movies);

        void onNoInternetAccess();

        void onResponseFailure();

    }

    interface Presenter{

        void setView(MainContract.View view);

        void onPopularMoviesListRequested(Context context);

        void onTopRatedMoviesListRequested(Context context);

        void onFavoriteMoviesRequested(AppCompatActivity appCompatActivity);

    }

}
