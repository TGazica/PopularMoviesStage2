package org.tomislavgazica.popularmovies.ui.movieList.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.tomislavgazica.popularmovies.R;
import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.ui.movieList.listener.OnMovieClickListener;
import org.tomislavgazica.popularmovies.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_item_movie)
    ImageView ivItemMovie;

    private OnMovieClickListener listener;

    public void setListener(OnMovieClickListener listener) {
        this.listener = listener;
    }

    public MovieListViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setMovie(Movie movie){
        Glide.with(itemView)
                .load(Constants.IMAGE_URL + "w185/" + movie.getPoster_path())
                .into(ivItemMovie);
        itemView.setTag(movie);
    }

    @OnClick
    public void onMovieClicked(){
        Movie movie = (Movie) itemView.getTag();
        listener.onMovieClickedListener(movie.getId());
    }

}
