package org.tomislavgazica.popularmovies.database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final FavoriteMoviesDatabase database;
    private final int movieId;

    public MovieViewModelFactory(FavoriteMoviesDatabase database, int movieId) {
        this.database = database;
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieViewModel(database, movieId);
    }
}
