package org.tomislavgazica.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.tomislavgazica.popularmovies.model.Review;

import java.util.List;



@Dao
public interface ReviewsDao {

    @Query("SELECT * FROM review WHERE movieId =:id ORDER BY id")
    LiveData<List<Review>> loadAllReviewsForMovie(int id);

    @Insert
    void insetReview(Review review);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateReview(Review review);

    @Delete
    void deleteReview(Review review);

}
