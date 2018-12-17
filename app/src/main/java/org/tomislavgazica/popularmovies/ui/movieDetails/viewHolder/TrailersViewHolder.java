package org.tomislavgazica.popularmovies.ui.movieDetails.viewHolder;

import android.view.View;
import android.widget.TextView;

import org.tomislavgazica.popularmovies.R;
import org.tomislavgazica.popularmovies.model.Trailer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.tv_trailer_item_trailer_number)
    TextView tvTrailerItemTrailerNumber;

    public TrailersViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setTrailer(int trailerNumber, Trailer trailer){
        itemView.setTag(trailer);

        tvTrailerItemTrailerNumber.setText(String.valueOf(trailerNumber));
    }

}
