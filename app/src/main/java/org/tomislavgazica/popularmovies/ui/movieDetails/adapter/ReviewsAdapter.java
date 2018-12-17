package org.tomislavgazica.popularmovies.ui.movieDetails.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tomislavgazica.popularmovies.R;
import org.tomislavgazica.popularmovies.model.Review;
import org.tomislavgazica.popularmovies.ui.movieDetails.viewHolder.ReviewViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_review, viewGroup, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        Review review = reviews.get(i);
        reviewViewHolder.setReview(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
