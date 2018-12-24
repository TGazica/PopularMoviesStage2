package org.tomislavgazica.popularmovies.ui.movieDetails.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tomislavgazica.popularmovies.R;
import org.tomislavgazica.popularmovies.model.Trailer;
import org.tomislavgazica.popularmovies.ui.movieDetails.listener.OnTrailerClickListener;
import org.tomislavgazica.popularmovies.ui.movieDetails.viewHolder.TrailersViewHolder;

import java.util.ArrayList;
import java.util.List;


public class TrailersAdapter extends RecyclerView.Adapter<TrailersViewHolder> {

    private List<Trailer> trailers = new ArrayList<>();
    private OnTrailerClickListener listener;

    public TrailersAdapter(OnTrailerClickListener listener) {
        this.listener = listener;
    }

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
        trailersViewHolder.setListener(listener);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
