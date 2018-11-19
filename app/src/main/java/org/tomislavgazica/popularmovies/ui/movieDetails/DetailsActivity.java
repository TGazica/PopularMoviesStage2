package org.tomislavgazica.popularmovies.ui.movieDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.tomislavgazica.popularmovies.App;
import org.tomislavgazica.popularmovies.R;
import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.presentation.DetailPresenter;
import org.tomislavgazica.popularmovies.ui.movieList.MainActivity;
import org.tomislavgazica.popularmovies.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View {

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

    private int movieId;
    private DetailsContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        presenter = new DetailPresenter(App.getApiInteractor());
        presenter.setView(this);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.MOVIE_KEY)) {
            movieId = intent.getIntExtra(MainActivity.MOVIE_KEY, 0);
        }

        presenter.onMovieDataFromDatabaseCalled(movieId, getApplicationContext());

    }

    @Override
    public void setUIData(Movie movie) {
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
    }

    @Override
    public void onNoInternetAccess() {
        Toast.makeText(getApplicationContext(), getString(R.string.on_no_internet_access_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailure() {
        Toast.makeText(getApplicationContext(), getString(R.string.on_response_failure_text), Toast.LENGTH_SHORT).show();
    }
}
