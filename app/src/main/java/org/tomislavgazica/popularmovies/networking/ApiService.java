package org.tomislavgazica.popularmovies.networking;

import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.MoviesResponse;
import org.tomislavgazica.popularmovies.model.ReviewsResponse;
import org.tomislavgazica.popularmovies.model.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("popular")
    Call<MoviesResponse> getPopularMoviesFromDatabase(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<MoviesResponse> getTopMoviesFromDatabase(@Query("api_key") String apiKey);

    @GET("{id}")
    Call<Movie> getMovieDetailsFromDatabase(@Path(value = "id", encoded = true) int movieId, @Query("api_key") String apyKey);

    @GET("{id}/videos")
    Call<TrailersResponse> getTrailersForMovie(@Path(value = "id", encoded = true) int movieId, @Query("api_key") String apyKey);

    @GET("{id}/reviews")
    Call<ReviewsResponse> getReviewsForMovie(@Path(value = "id", encoded = true) int movieId, @Query("api_key") String apyKey);

}
