package org.tomislavgazica.popularmovies.interactor;

import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.MoviesResponse;
import org.tomislavgazica.popularmovies.model.ReviewsResponse;
import org.tomislavgazica.popularmovies.model.TrailersResponse;
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
    public void getTopMoviesFromDatabase(Callback<MoviesResponse> callback, String apiKey) {
        apiService.getTopMoviesFromDatabase(apiKey).enqueue(callback);
    }

    @Override
    public void getMovieDetailsFromDatabase(Callback<Movie> callback, int movieId, String apyKey) {
        apiService.getMovieDetailsFromDatabase(movieId, apyKey).enqueue(callback);
    }

    @Override
    public void getTrailersForMovie(Callback<TrailersResponse> callback, int movieId, String apyKey) {
        apiService.getTrailersForMovie(movieId, apyKey).enqueue(callback);
    }

    @Override
    public void getReviewsForMovie(Callback<ReviewsResponse> callback, int movieId, String apyKey) {
        apiService.getReviewsForMovie(movieId, apyKey).enqueue(callback);
    }
}
