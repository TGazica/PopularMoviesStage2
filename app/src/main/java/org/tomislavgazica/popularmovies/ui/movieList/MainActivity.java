package org.tomislavgazica.popularmovies.ui.movieList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.tomislavgazica.popularmovies.App;
import org.tomislavgazica.popularmovies.R;
import org.tomislavgazica.popularmovies.model.Movie;
import org.tomislavgazica.popularmovies.presentation.MainPresenter;
import org.tomislavgazica.popularmovies.ui.movieDetails.DetailsActivity;
import org.tomislavgazica.popularmovies.ui.movieList.adapter.MovieListAdapter;
import org.tomislavgazica.popularmovies.ui.movieList.listener.OnMovieClickListener;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View, OnMovieClickListener {

    public static final String MOVIE_KEY = "movie";

    @BindView(R.id.rv_main_movies_list)
    RecyclerView rvMainMoviesList;

    private MainContract.Presenter presenter;
    private MovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(App.getApiInteractor());
        presenter.setView(this);

        adapter = new MovieListAdapter();
        adapter.setListener(this);

        rvMainMoviesList.setLayoutManager(new GridLayoutManager(this, 2));
        rvMainMoviesList.setAdapter(adapter);

        presenter.onPopularMoviesListRequested(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_show_popular:
                presenter.onPopularMoviesListRequested(getApplicationContext());
                break;
            case R.id.menu_item_show_top_rated:
                presenter.onTopRatedMoviesListRequested(getApplicationContext());
                break;
            default:
                presenter.onPopularMoviesListRequested(getApplicationContext());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClickedListener(int id) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(MOVIE_KEY, id);
        startActivity(intent);
    }

    @Override
    public void setMovieList(List<Movie> movies) {
        adapter.setMovies(movies);
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
