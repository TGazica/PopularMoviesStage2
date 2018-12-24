package org.tomislavgazica.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.Review;
import org.tomislavgazica.popularmovies.model.Trailer;



@Database(entities = {Movie.class}, version = 1, exportSchema = false)
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

}
