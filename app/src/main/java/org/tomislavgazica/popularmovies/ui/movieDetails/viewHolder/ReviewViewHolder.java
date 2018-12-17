package org.tomislavgazica.popularmovies.ui.movieDetails.viewHolder;

import android.view.View;
import android.widget.TextView;

import org.tomislavgazica.popularmovies.R;
import org.tomislavgazica.popularmovies.model.Review;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_item_review_name)
    TextView tvItemReviewName;
    @BindView(R.id.tv_item_review_text)
    TextView tvItemReviewText;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setReview(Review review){
        itemView.setTag(review);

        tvItemReviewName.setText(review.getAuthor());
        tvItemReviewText.setText(review.getContent());
    }
}
