package org.tomislavgazica.popularmovies.presentation;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.tomislavgazica.popularmovies.BuildConfig;
import org.tomislavgazica.popularmovies.database.FavoriteMoviesViewModel;
import org.tomislavgazica.popularmovies.interactor.ApiInteractor;
import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.MoviesResponse;
import org.tomislavgazica.popularmovies.ui.movieList.MainContract;
import org.tomislavgazica.popularmovies.util.NetworkUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private ApiInteractor apiInteractor;
    private MainContract.View view;
    private String apyKey;

    public MainPresenter(ApiInteractor apiInteractor) {
        this.apiInteractor = apiInteractor;
        apyKey = BuildConfig.API_KEY;
    }

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onPopularMoviesListRequested(Context context) {
        if (NetworkUtil.isThereInternetConnection(context)) {
            apiInteractor.getMoviesFromDatabase(getMoviesCallback(), apyKey);
        }else {
            view.onNoInternetAccess();
        }
    }

    @Override
    public void onTopRatedMoviesListRequested(Context context) {
        if (NetworkUtil.isThereInternetConnection(context)) {
            apiInteractor.getTopMoviesFromDatabase(getMoviesCallback(), apyKey);
        }else {
            view.onNoInternetAccess();
        }
    }

    private Callback<MoviesResponse> getMoviesCallback() {
        return new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.body() != null){
                    view.setMovieList(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                view.onResponseFailure();
            }
        };
    }

    @Override
    public void onFavoriteMoviesRequested(AppCompatActivity appCompatActivity) {
        FavoriteMoviesViewModel favoriteMoviesViewModel = ViewModelProviders.of(appCompatActivity).get(FavoriteMoviesViewModel.class);
        favoriteMoviesViewModel.getMovies().observe(appCompatActivity, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                view.setMovieList(movies);
            }
        });

    }
}
