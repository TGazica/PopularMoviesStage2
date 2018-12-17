package org.tomislavgazica.popularmovies.ui.movieDetails.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tomislavgazica.popularmovies.R;
import org.tomislavgazica.popularmovies.model.Trailer;
import org.tomislavgazica.popularmovies.ui.movieDetails.viewHolder.TrailersViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersViewHolder> {

    private List<Trailer> trailers = new ArrayList<>();

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_trailer, viewGroup, false);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder trailersViewHolder, int i) {
        Trailer trailer = trailers.get(i);
        trailersViewHolder.setTrailer(i, trailer);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
