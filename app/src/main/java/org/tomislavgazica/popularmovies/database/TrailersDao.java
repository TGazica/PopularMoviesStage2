package org.tomislavgazica.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.tomislavgazica.popularmovies.model.Trailer;

import java.util.List;


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
