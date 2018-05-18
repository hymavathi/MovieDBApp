package com.udacity.projects.moviesdbapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.udacity.projects.moviesdbapp.adapters.MovieRecyclerViewAdapter;
import com.udacity.projects.moviesdbapp.model.Movie;
import com.udacity.projects.moviesdbapp.util.AppConstants;
import com.udacity.projects.moviesdbapp.util.MovieUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MovieRecyclerViewAdapter adapter;
    private static final int NUMBER_OF_COLUMNS = 2;
    private RecyclerView recyclerView;
    MovieRecyclerViewAdapter.ItemClickListener itemClickListener;

    private int sort_order = MovieAsyncTask.POPULAR_SORT_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // set up the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.moviesrview);
        if (MovieUtil.isNetworkActive(this)) {
            new MovieAsyncTask().execute();
        } else {
            Toast.makeText(getBaseContext(), "Network is not available", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_popular_movies:
                sort_order = MovieAsyncTask.POPULAR_SORT_ID;
                new MovieAsyncTask().execute();
                return true;
            case R.id.menu_top_rated_movies:
                sort_order = MovieAsyncTask.RATING_SORT_ID;
                new MovieAsyncTask().execute();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        Intent detailsIntent = new Intent(this, DetailItemActivity.class);

        startActivity(detailsIntent);
    }


    private class MovieAsyncTask extends AsyncTask<Void, Void, Object> {

        static final int POPULAR_SORT_ID = 1;
        static final int RATING_SORT_ID = 2;

        @Override
        protected Object doInBackground(Void... voids) {

            switch (sort_order) {

                case MovieAsyncTask.POPULAR_SORT_ID:
                    return MovieUtil.getPopularMovies();
                case MovieAsyncTask.RATING_SORT_ID:
                    return MovieUtil.getTopRatedMovies();

                default:
                    return MovieUtil.getPopularMovies();

            }

        }

        @Override
        protected void onPostExecute(final Object data) {
            adapter = new MovieRecyclerViewAdapter(MainActivity.this, (List<Movie>) data, new MovieRecyclerViewAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent detailsIntent = new Intent(MainActivity.this, DetailItemActivity.class);
                    Movie movie = ((List<Movie>) data).get(position);

                    detailsIntent.putExtra(AppConstants.MOVIE_INTENT_KEY, movie);
                    startActivity(detailsIntent);
                }
            });

            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this
                    , NUMBER_OF_COLUMNS));
            recyclerView.setAdapter(adapter);

        }
    }
}
