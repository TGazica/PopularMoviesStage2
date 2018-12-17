package org.tomislavgazica.popularmovies.database;

import android.content.Context;

import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.Review;
import org.tomislavgazica.popularmovies.model.Trailer;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class, Trailer.class, Review.class}, version = 1, exportSchema = false)
public abstract class FavoriteMoviesDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favoriteMoviesList";
    private static FavoriteMoviesDatabase sInstance;

    public static FavoriteMoviesDatabase getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context, FavoriteMoviesDatabase.class, DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract FavoriteMoviesDao getFavoriteMoviesDao();
    public abstract TrailersDao getTrailersDao();
    public abstract ReviewsDao getReviewsDao();

}
