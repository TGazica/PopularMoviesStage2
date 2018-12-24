package org.tomislavgazica.popularmovies.ui.movieDetails.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.tomislavgazica.popularmovies.R;
import org.tomislavgazica.popularmovies.model.Trailer;
import org.tomislavgazica.popularmovies.ui.movieDetails.listener.OnTrailerClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrailersViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.tv_trailer_item_trailer_number)
    TextView tvTrailerItemTrailerNumber;
    @BindView(R.id.iv_item_trailer_share)
    ImageView ivItemTrailerShare;

    private OnTrailerClickListener listener;

    public void setListener(OnTrailerClickListener listener) {
        this.listener = listener;
    }

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
        Trailer trailer = (Trailer) itemView.getTag();
        listener.onTrailerClicked(trailer);
    }

    @OnClick(R.id.iv_item_trailer_share)
    public void onShareClicked(){
        Trailer trailer = (Trailer) itemView.getTag();
        listener.onTrailerShareClicked(trailer);
    }

}
