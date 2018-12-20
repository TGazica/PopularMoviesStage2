package org.tomislavgazica.popularmovies.ui.movieDetails.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.tomislavgazica.popularmovies.R;
import org.tomislavgazica.popularmovies.model.Trailer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrailersViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.tv_trailer_item_trailer_number)
    TextView tvTrailerItemTrailerNumber;
    @BindView(R.id.iv_item_trailer_share)
    ImageView ivItemTrailerShare;

    public TrailersViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setTrailer(int trailerNumber, Trailer trailer) {
        itemView.setTag(trailer);

        tvTrailerItemTrailerNumber.setText(String.valueOf(trailerNumber));
        setShareVisibility();
    }

    public void setShareVisibility() {
        if (getAdapterPosition() == 0) {
            ivItemTrailerShare.setVisibility(View.VISIBLE);
        } else {
            ivItemTrailerShare.setVisibility(View.GONE);
        }
    }

    @OnClick
    public void onTrailerClicked(){

    }

    @OnClick(R.id.iv_item_trailer_share)
    public void onShareClicked(){

    }

}
