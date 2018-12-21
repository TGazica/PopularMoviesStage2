package org.tomislavgazica.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.tomislavgazica.popularmovies.model.Movie;

import java.util.List;



@Dao
public interface FavoriteMoviesDao {

    @Query("SELECT * FROM favoriteMovies ORDER BY id")
    LiveData<List<Movie>> loadAllFavoriteMovies();

    @Query("SELECT * FROM favoriteMovies WHERE id =:movieId")
    Movie loadMovieFromDatabase(int movieId);

    @Insert
    void insertFavoriteMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavoriteMovie(Movie movie);

    @Delete
    void deleteFavoriteMovie(Movie movie);

}
