package org.tomislavgazica.popularmovies.interactor;

import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.MoviesResponse;
import org.tomislavgazica.popularmovies.model.ReviewsResponse;
import org.tomislavgazica.popularmovies.model.TrailersResponse;

import retrofit2.Callback;

public interface ApiInteractor {

    void getMoviesFromDatabase(Callback<MoviesResponse> callback, String apiKey);

    void getTopMoviesFromDatabase(Callback<MoviesResponse> callback, String apiKey);

    void getMovieDetailsFromDatabase(Callback<Movie> callback, int movieId, String apyKey);

    void getTrailersForMovie(Callback<TrailersResponse> callback, int movieId, String apyKey);

    void getReviewsForMovie(Callback<ReviewsResponse> callback, int movieId, String apyKey);

}
