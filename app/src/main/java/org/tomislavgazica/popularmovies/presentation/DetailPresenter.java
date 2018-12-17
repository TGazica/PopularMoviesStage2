package org.tomislavgazica.popularmovies.presentation;

import android.content.Context;

import org.tomislavgazica.popularmovies.BuildConfig;
import org.tomislavgazica.popularmovies.database.FavoriteMoviesDatabase;
import org.tomislavgazica.popularmovies.interactor.ApiInteractor;
import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.ReviewsResponse;
import org.tomislavgazica.popularmovies.model.TrailersResponse;
import org.tomislavgazica.popularmovies.ui.movieDetails.DetailsContract;
import org.tomislavgazica.popularmovies.util.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements DetailsContract.Presenter {

    private DetailsContract.View view;
    private ApiInteractor apiInteractor;
    private String apyKey;
    private FavoriteMoviesDatabase database;
    private Movie movie;
    private boolean isMovieFavorite = false;

    public DetailPresenter(ApiInteractor apiInteractor, Context context) {
        this.apiInteractor = apiInteractor;
        apyKey = BuildConfig.API_KEY;
        database = FavoriteMoviesDatabase.getInstance(context);
    }

    @Override
    public void setView(DetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void onMovieDataFromDatabaseCalled(int id, Context context) {
        if (!getMovieFromDatabaseFavorites(id) && NetworkUtil.isThereInternetConnection(context)) {
            apiInteractor.getMovieDetailsFromDatabase(getMovieCallback(), id, apyKey);
        } else {
            view.onNoInternetAccess();
        }
    }

    private Callback<Movie> getMovieCallback() {
        return new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.body() != null) {
                    movie = response.body();
                    view.setMovieData(response.body(), false);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                view.onResponseFailure();
            }
        };
    }

    @Override
    public void onTrailersFormDatabaseCalled(int id, Context context) {
        if (NetworkUtil.isThereInternetConnection(context)) {
            apiInteractor.getTrailersForMovie(getTrailersCallback(), id, apyKey);
        } else {
            view.onNoInternetAccess();
        }
    }

    private Callback<TrailersResponse> getTrailersCallback() {
        return new Callback<TrailersResponse>() {
            @Override
            public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
                if (response.body() != null) {
                    view.setTrailerList(response.body().getTrailers());
                }
            }

            @Override
            public void onFailure(Call<TrailersResponse> call, Throwable t) {
                view.onResponseFailure();
            }
        };
    }

    @Override
    public void onReviewsFromDatabaseCalled(int id, Context context) {
        if (NetworkUtil.isThereInternetConnection(context)) {
            apiInteractor.getReviewsForMovie(getReviewsCallback(), id, apyKey);
        } else {
            view.onNoInternetAccess();
        }
    }

    private Callback<ReviewsResponse> getReviewsCallback() {
        return new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                if (response.body() != null) {
                    view.setReviewList(response.body().getReviews());
                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                view.onResponseFailure();
            }
        };
    }

    private boolean getMovieFromDatabaseFavorites(int movieId) {
        movie = database.getFavoriteMoviesDao().loadMovieFromDatabase(movieId);
        if (movie != null) {
            isMovieFavorite = true;
            view.setMovieData(movie, isMovieFavorite);
        } else {
            isMovieFavorite = false;

        }
        return isMovieFavorite;
    }

    @Override
    public void onMovieFavoriteStateChanged() {
        if (isMovieFavorite){
            database.getFavoriteMoviesDao().deleteFavoriteMovie(movie);
        }else {
            database.getFavoriteMoviesDao().insetFavoriteMovie(movie);
        }
    }
}
