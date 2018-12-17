package org.tomislavgazica.popularmovies.ui.movieDetails;

import android.content.Context;

import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.Review;
import org.tomislavgazica.popularmovies.model.Trailer;

import java.util.List;

public interface DetailsContract {

    interface View{

        void setMovieData(Movie movie, boolean isFavorite);

        void setTrailerList(List<Trailer> trailerList);

        void setReviewList(List<Review> reviewList);

        void onNoInternetAccess();

        void onResponseFailure();

    }

    interface Presenter{

        void setView(DetailsContract.View view);

        void onMovieDataFromDatabaseCalled(int id, Context context);

        void onTrailersFormDatabaseCalled(int id, Context context);

        void onReviewsFromDatabaseCalled(int id, Context context);

        void onMovieFavoriteStateChanged();

    }

}
