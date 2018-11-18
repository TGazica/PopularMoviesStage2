package org.tomislavgazica.popularmovies.interactor;

import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.MoviesResponse;
import org.tomislavgazica.popularmovies.networking.ApiService;

import retrofit2.Callback;

public class ApiInteractorImpl implements ApiInteractor{

    private final ApiService apiService;

    public ApiInteractorImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getMoviesFromDatabase(Callback<MoviesResponse> callback, String apiKey) {
        apiService.getPopularMoviesFromDatabase(apiKey).enqueue(callback);
    }

    @Override
    public void getMovieDetailsFromDatabase(Callback<Movie> callback, int movieId, String apyKey) {
        apiService.getMovieDetailsFromDatabase(movieId, apyKey).enqueue(callback);
    }
}
