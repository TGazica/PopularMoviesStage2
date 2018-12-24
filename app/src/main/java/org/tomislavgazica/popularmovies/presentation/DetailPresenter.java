package org.tomislavgazica.popularmovies.presentation;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.tomislavgazica.popularmovies.BuildConfig;
import org.tomislavgazica.popularmovies.database.FavoriteMoviesDatabase;
import org.tomislavgazica.popularmovies.database.MovieViewModel;
import org.tomislavgazica.popularmovies.database.MovieViewModelFactory;
import org.tomislavgazica.popularmovies.interactor.ApiInteractor;
import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.Review;
import org.tomislavgazica.popularmovies.model.ReviewsResponse;
import org.tomislavgazica.popularmovies.model.Trailer;
import org.tomislavgazica.popularmovies.model.TrailersResponse;
import org.tomislavgazica.popularmovies.ui.movieDetails.DetailsContract;
import org.tomislavgazica.popularmovies.util.AppExecutors;
import org.tomislavgazica.popularmovies.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements DetailsContract.Presenter {

    private DetailsContract.View view;
    private ApiInteractor apiInteractor;
    private String apyKey;
    private FavoriteMoviesDatabase database;
    private Movie currentMovie;
    private List<Trailer> trailers = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private AppExecutors appExecutors;
    private boolean isMovieFavorite;

    public DetailPresenter(ApiInteractor apiInteractor, Context context) {
        this.apiInteractor = apiInteractor;
        apyKey = BuildConfig.API_KEY;
        database = FavoriteMoviesDatabase.getInstance(context);
        appExecutors = AppExecutors.getInstance();
    }

    @Override
    public void setView(DetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void onMovieDataFromDatabaseCalled(int id, Context context, AppCompatActivity appCompatActivity) {
        getMovieFromFavoritesDatabase(id, context, appCompatActivity);
    }

    private void getMovieFromFavoritesDatabase(final int movieId, final Context context, AppCompatActivity appCompatActivity) {
        MovieViewModelFactory factory = new MovieViewModelFactory(database, movieId);
        final MovieViewModel viewModel = ViewModelProviders.of(appCompatActivity, factory).get(MovieViewModel.class);
        viewModel.getMovie().observe(appCompatActivity, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                currentMovie = movie;
                viewModel.getMovie().removeObserver(this);
                isMovieFavorite(movie, movieId, context);
            }
        });
    }

    private void isMovieFavorite(Movie movie, int movieId, Context context) {
        if (movie != null) {
            isMovieFavorite = true;
            view.setMovieData(movie, isMovieFavorite);
        } else {
            isMovieFavorite = false;
            if (NetworkUtil.isThereInternetConnection(context)) {
                apiInteractor.getMovieDetailsFromDatabase(getMovieCallback(), movieId, apyKey);
            }
        }

        if (NetworkUtil.isThereInternetConnection(context)) {
            apiInteractor.getTrailersForMovie(getTrailersCallback(), movieId, apyKey);
            apiInteractor.getReviewsForMovie(getReviewsCallback(), movieId, apyKey);
        }else {
            view.onNoInternetAccess();
        }
    }

    private Callback<Movie> getMovieCallback() {
        return new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.body() != null) {
                    currentMovie = response.body();
                    view.setMovieData(currentMovie, isMovieFavorite);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                view.onResponseFailure();
            }
        };
    }

    private Callback<TrailersResponse> getTrailersCallback() {
        return new Callback<TrailersResponse>() {
            @Override
            public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
                if (response.body() != null) {
                    trailers = response.body().getTrailers();
                    view.setTrailerList(trailers);
                }
            }

            @Override
            public void onFailure(Call<TrailersResponse> call, Throwable t) {
                view.onResponseFailure();
            }
        };
    }

    private Callback<ReviewsResponse> getReviewsCallback() {
        return new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                if (response.body() != null) {
                    reviews = response.body().getReviews();
                    view.setReviewList(reviews);
                }
            }

            @Override
            public void onFailure(Call<ReviewsResponse> call, Throwable t) {
                view.onResponseFailure();
            }
        };
    }

    @Override
    public void onMovieFavoriteStateChanged(AppCompatActivity appCompatActivity) {
        MovieViewModelFactory factory = new MovieViewModelFactory(database, currentMovie.getId());
        final MovieViewModel viewModel = ViewModelProviders.of(appCompatActivity, factory).get(MovieViewModel.class);
        viewModel.getMovie().observe(appCompatActivity, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable final Movie movie) {
                viewModel.getMovie().removeObserver(this);
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (movie != null){
                            database.getFavoriteMoviesDao().deleteFavoriteMovie(movie);
                        }else {
                            database.getFavoriteMoviesDao().insertFavoriteMovie(currentMovie);
                        }
                    }
                });
            }
        });
    }
}
