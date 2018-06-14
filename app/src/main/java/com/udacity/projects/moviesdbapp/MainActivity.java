package com.udacity.projects.moviesdbapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
    ProgressBar progressBar;

    //For instance state save and restore
    private static final String SORT_ORDER_KEY = "SORT_ORDER";
    private static final String RV_POSITION_KEY = "RV_KEY";
    private Parcelable parcelable;

    private int sort_order = MovieAsyncTask.POPULAR_SORT_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // set up the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.moviesrview);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // Check if Network is available before making a call to API.
        if (MovieUtil.isNetworkActive(this)) {
            if (savedInstanceState != null && savedInstanceState.containsKey(SORT_ORDER_KEY)) {
                sort_order = savedInstanceState.getInt(SORT_ORDER_KEY);
            }
            new MovieAsyncTask().execute();

        } else {
            Log.e(TAG, "onCreate: Network is not available ");

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
        if (MovieUtil.isNetworkActive(this)) {

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
        } else {
            Toast.makeText(getBaseContext(), "Network is not available", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        Intent detailsIntent = new Intent(this, DetailItemActivity.class);

        startActivity(detailsIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SORT_ORDER_KEY , sort_order );
        Log.d("onSaveInstanceState", "onSaveInstanceState()" + outState.getInt(SORT_ORDER_KEY));

        if(recyclerView != null && recyclerView.getLayoutManager() != null) {
            outState.putParcelable(RV_POSITION_KEY , recyclerView.getLayoutManager().onSaveInstanceState());
        }
    }

   @Override
   protected void onRestoreInstanceState(Bundle savedInstanceState) {

        if(savedInstanceState!= null) {
           sort_order = savedInstanceState.containsKey(SORT_ORDER_KEY )? savedInstanceState.getInt(SORT_ORDER_KEY) : sort_order;
           parcelable =  savedInstanceState.getParcelable(RV_POSITION_KEY);
            Log.d(TAG, "onRestoreInstanceState: " + sort_order);
        }
       super.onRestoreInstanceState(savedInstanceState);

   }

    private class MovieAsyncTask extends AsyncTask<Void, Void, Object> {

        static final int POPULAR_SORT_ID = 1;
        static final int RATING_SORT_ID = 2;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected Object doInBackground(Void... voids) {
            if (MovieUtil.isNetworkActive(MainActivity.this)) {

                switch (sort_order) {

                    case MovieAsyncTask.POPULAR_SORT_ID:
                        return MovieUtil.getPopularMovies();
                    case MovieAsyncTask.RATING_SORT_ID:
                        return MovieUtil.getTopRatedMovies();

                    default:
                        return MovieUtil.getPopularMovies();

                }
            } else {
                Toast.makeText(getBaseContext(), "Network is not available", Toast.LENGTH_LONG).show();
                return null;
            }

        }

        @Override
        protected void onPostExecute(final Object data) {
            progressBar.setVisibility(View.INVISIBLE);

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
            recyclerView.getLayoutManager().onRestoreInstanceState(parcelable);

        }
    }
}
