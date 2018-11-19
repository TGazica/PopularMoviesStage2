package org.tomislavgazica.popularmovies.interactor;

import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.MoviesResponse;

import retrofit2.Callback;

public interface ApiInteractor {

    void getMoviesFromDatabase(Callback<MoviesResponse> callback, String apiKey);

    void getTopMoviesFromDatabase(Callback<MoviesResponse> callback, String apiKey);

    void getMovieDetailsFromDatabase(Callback<Movie> callback, int movieId, String apyKey);

}
