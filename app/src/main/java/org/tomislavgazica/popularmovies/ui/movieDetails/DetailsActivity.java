package org.tomislavgazica.popularmovies.ui.movieDetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.tomislavgazica.popularmovies.App;
import org.tomislavgazica.popularmovies.R;
import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.model.Review;
import org.tomislavgazica.popularmovies.model.Trailer;
import org.tomislavgazica.popularmovies.presentation.DetailPresenter;
import org.tomislavgazica.popularmovies.ui.movieDetails.adapter.ReviewsAdapter;
import org.tomislavgazica.popularmovies.ui.movieDetails.adapter.TrailersAdapter;
import org.tomislavgazica.popularmovies.ui.movieDetails.listener.OnTrailerClickListener;
import org.tomislavgazica.popularmovies.ui.movieList.MainActivity;
import org.tomislavgazica.popularmovies.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View, OnTrailerClickListener {

    @BindView(R.id.tv_details_movie_title)
    TextView tvDetailsMovieTitle;
    @BindView(R.id.iv_detail_movie_poster)
    ImageView ivDetailMoviePoster;
    @BindView(R.id.tv_detail_movie_release_year)
    TextView tvDetailMovieReleaseYear;
    @BindView(R.id.tv_detail_movie_length)
    TextView tvDetailMovieLength;
    @BindView(R.id.tv_detail_movie_score)
    TextView tvDetailMovieScore;
    @BindView(R.id.tv_detail_movie_synopsis)
    TextView tvDetailMovieSynopsis;
    @BindView(R.id.cb_detail_movie_favorite)
    CheckBox cbDetailMovieFavorite;
    @BindView(R.id.rv_detail_movie_trailers)
    RecyclerView rvDetailMovieTrailers;
    @BindView(R.id.rv_detail_movie_reviews)
    RecyclerView rvDetailMovieReviews;

    private DetailsContract.Presenter presenter;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        trailersAdapter = new TrailersAdapter(this);
        reviewsAdapter = new ReviewsAdapter();

        rvDetailMovieTrailers.setLayoutManager(new LinearLayoutManager(this));
        rvDetailMovieReviews.setLayoutManager(new LinearLayoutManager(this));

        rvDetailMovieTrailers.setAdapter(trailersAdapter);
        rvDetailMovieReviews.setAdapter(reviewsAdapter);

        presenter = new DetailPresenter(App.getApiInteractor(), this);
        presenter.setView(this);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.MOVIE_KEY)) {
            int movieId = intent.getIntExtra(MainActivity.MOVIE_KEY, 0);
            presenter.onMovieDataFromDatabaseCalled(movieId, this, this);
        }
    }

    @OnClick(R.id.cb_detail_movie_favorite)
    public void onFavoriteClicked() {
        presenter.onMovieFavoriteStateChanged(this);
    }

    @Override
    public void setMovieData(final Movie movie, final boolean isMovieFavorite) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(getApplicationContext())
                        .load(Constants.IMAGE_URL + Constants.IMAGE_SIZE + movie.getPoster_path())
                        .into(ivDetailMoviePoster);
                tvDetailsMovieTitle.setText(movie.getTitle());
                tvDetailMovieReleaseYear.setText(movie.getRelease_date());
                String length = movie.getRuntime() + getString(R.string.minutes);
                tvDetailMovieLength.setText(length);
                String score = movie.getVote_average() + "/10";
                tvDetailMovieScore.setText(score);
                tvDetailMovieSynopsis.setText(movie.getOverview());
                cbDetailMovieFavorite.setChecked(isMovieFavorite);
            }
        });
    }

    @Override
    public void setTrailerList(final List<Trailer> trailerList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                trailersAdapter.setTrailers(trailerList);
            }
        });
    }

    @Override
    public void setReviewList(final List<Review> reviewList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                reviewsAdapter.setReviews(reviewList);
            }
        });

    }

    @Override
    public void onNoInternetAccess() {
        Toast.makeText(getApplicationContext(), getString(R.string.on_no_internet_access_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailure() {
        Toast.makeText(getApplicationContext(), getString(R.string.on_response_failure_text), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTrailerClicked(Trailer trailer) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey())));
    }

    @Override
    public void onTrailerShareClicked(Trailer trailer) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
        i.putExtra(Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v=" + trailer.getKey());
        startActivity(Intent.createChooser(i, "Share URL"));
    }
}
