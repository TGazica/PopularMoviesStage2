package org.tomislavgazica.popularmovies.database;

import org.tomislavgazica.popularmovies.model.Review;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
