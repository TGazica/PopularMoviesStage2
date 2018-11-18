package org.tomislavgazica.popularmovies.presentation;

import android.content.Context;

import org.tomislavgazica.popularmovies.interactor.ApiInteractor;
import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.ui.movieDetails.DetailsContract;
import org.tomislavgazica.popularmovies.util.Constants;
import org.tomislavgazica.popularmovies.util.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter implements DetailsContract.Presenter {

    private DetailsContract.View view;
    private ApiInteractor apiInteractor;

    public DetailPresenter(ApiInteractor apiInteractor) {
        this.apiInteractor = apiInteractor;
    }

    @Override
    public void setView(DetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void onMovieDataFromDatabaseCalled(int id, Context context) {
        if (NetworkUtil.isThereInternetConnection(context)) {
            apiInteractor.getMovieDetailsFromDatabase(getMovieCallback(), id, Constants.API_KEY);
        }else {
            view.onNoInternetAccess();
        }
    }

    private Callback<Movie> getMovieCallback() {
        return new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.body() != null){
                    view.setUIData(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                view.onResponseFailure();
            }
        };
    }
}
