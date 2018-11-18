package org.tomislavgazica.popularmovies;

import android.app.Application;

import org.tomislavgazica.popularmovies.interactor.ApiInteractor;
import org.tomislavgazica.popularmovies.interactor.ApiInteractorImpl;
import org.tomislavgazica.popularmovies.networking.ApiService;
import org.tomislavgazica.popularmovies.networking.RetrofitUtil;

import retrofit2.Retrofit;

public class App extends Application {

    private static App appInstance;
    private static ApiInteractor apiInteractor;

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        final Retrofit retrofit = RetrofitUtil.createRetrofit();
        final ApiService apiService = retrofit.create(ApiService.class);

        apiInteractor = new ApiInteractorImpl(apiService);
    }

    public static ApiInteractor getApiInteractor() {
        return apiInteractor;
    }
}
