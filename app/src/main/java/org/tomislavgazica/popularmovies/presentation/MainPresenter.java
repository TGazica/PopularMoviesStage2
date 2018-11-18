package org.tomislavgazica.popularmovies.presentation;

import android.content.Context;

import org.tomislavgazica.popularmovies.interactor.ApiInteractor;
import org.tomislavgazica.popularmovies.model.MoviesResponse;
import org.tomislavgazica.popularmovies.ui.movieList.MainContract;
import org.tomislavgazica.popularmovies.util.Constants;
import org.tomislavgazica.popularmovies.util.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private ApiInteractor apiInteractor;
    private MainContract.View view;

    public MainPresenter(ApiInteractor apiInteractor) {
        this.apiInteractor = apiInteractor;
    }

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onMovieListRequested(Context context) {
        if (NetworkUtil.isThereInternetConnection(context)) {
            apiInteractor.getMoviesFromDatabase(getMoviesCallback(), Constants.API_KEY);
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
}
