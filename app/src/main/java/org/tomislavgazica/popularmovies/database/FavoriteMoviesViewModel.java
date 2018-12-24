package org.tomislavgazica.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import org.tomislavgazica.popularmovies.model.Movie;

import java.util.List;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);

        FavoriteMoviesDatabase database = FavoriteMoviesDatabase.getInstance(this.getApplication());
        movies = database.getFavoriteMoviesDao().loadAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
