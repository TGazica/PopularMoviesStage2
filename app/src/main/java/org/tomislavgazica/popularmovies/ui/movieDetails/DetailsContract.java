package org.tomislavgazica.popularmovies.ui.movieDetails;

import android.content.Context;

import org.tomislavgazica.popularmovies.model.Movie;

public interface DetailsContract {

    interface View{

        void setUIData(Movie movie);

        void onNoInternetAccess();

        void onResponseFailure();

    }

    interface Presenter{

        void setView(DetailsContract.View view);

        void onMovieDataFromDatabaseCalled(int id, Context context);

    }

}
