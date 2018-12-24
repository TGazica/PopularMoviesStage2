package org.tomislavgazica.popularmovies.ui.movieList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View, OnMovieClickListener {

    public static final String MOVIE_KEY = "movie";
    private static final String RECYCLER_VIEW_STATE = "recyclerViewState";
    private static final String CURRENT_SELECTED_MENU = "currentMenu";

    @BindView(R.id.rv_main_movies_list)
    RecyclerView rvMainMoviesList;

    private MainContract.Presenter presenter;
    private MovieListAdapter adapter;
    private Parcelable parcelable;
    private String currentMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null && savedInstanceState.getParcelable(RECYCLER_VIEW_STATE) != null){
            parcelable = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
        }

        if (savedInstanceState != null && savedInstanceState.getString(CURRENT_SELECTED_MENU) != null){
            currentMenu = savedInstanceState.getString(CURRENT_SELECTED_MENU);
        }else {
            currentMenu = getString(R.string.menu_item_popular_title);
        }

        presenter = new MainPresenter(App.getApiInteractor());
        presenter.setView(this);

        adapter = new MovieListAdapter();
        adapter.setListener(this);

        rvMainMoviesList.setLayoutManager(new GridLayoutManager(this, 2));
        rvMainMoviesList.setAdapter(adapter);

        initData();
    }

    private void initData(){
        if(currentMenu.equals(getString(R.string.menu_item_show_favorites_title))){
            presenter.onFavoriteMoviesRequested(this);
        }else if (currentMenu.equals(getString(R.string.menu_item_popular_title))){
            presenter.onPopularMoviesListRequested(getApplicationContext());
        }else {
            presenter.onTopRatedMoviesListRequested(getApplicationContext());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        currentMenu = (String) item.getTitle();

        switch (item.getItemId()) {
            case R.id.menu_item_show_popular:
                presenter.onPopularMoviesListRequested(getApplicationContext());
                break;
            case R.id.menu_item_show_top_rated:
                presenter.onTopRatedMoviesListRequested(getApplicationContext());
                break;

            case R.id.menu_item_show_favorites:
                presenter.onFavoriteMoviesRequested(this);
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
        if (parcelable != null && rvMainMoviesList != null && rvMainMoviesList.getLayoutManager() != null){
            rvMainMoviesList.getLayoutManager().onRestoreInstanceState(parcelable);
            parcelable = null;
        }
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
    protected void onPause() {
        super.onPause();

        if (rvMainMoviesList != null && rvMainMoviesList.getLayoutManager() != null){
            parcelable = rvMainMoviesList.getLayoutManager().onSaveInstanceState();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(RECYCLER_VIEW_STATE, parcelable);
        outState.putString(CURRENT_SELECTED_MENU, currentMenu);
    }
}
