package org.tomislavgazica.popularmovies.database;

import org.tomislavgazica.popularmovies.model.Trailer;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TrailersDao {

    @Query("SELECT * FROM trailer WHERE movieId =:id ORDER BY id")
    LiveData<List<Trailer>> loadAllTrailersForMovie(int id);

    @Insert
    void insetTrailer(Trailer trailer);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTrailer(Trailer trailer);

    @Delete
    void deleteTrailer(Trailer trailer);

}
