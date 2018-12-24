package org.tomislavgazica.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import org.tomislavgazica.popularmovies.model.Movie;

public class MovieViewModel extends ViewModel {

    private LiveData<Movie> movie;

    public MovieViewModel(FavoriteMoviesDatabase database, int movieId) {
        this.movie = database.getFavoriteMoviesDao().loadMovieFromDatabase(movieId);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
