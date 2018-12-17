package org.tomislavgazica.popularmovies.database;

import org.tomislavgazica.popularmovies.model.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FavoriteMoviesDao {

    @Query("SELECT * FROM favoriteMovies ORDER BY id")
    LiveData<List<Movie>> loadAllFavoriteMovies();

    @Query("SELECT * FROM favoriteMovies WHERE id =:movieId")
    Movie loadMovieFromDatabase(int movieId);

    @Insert
    void insetFavoriteMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavoriteMovie(Movie movie);

    @Delete
    void deleteFavoriteMovie(Movie movie);

}
