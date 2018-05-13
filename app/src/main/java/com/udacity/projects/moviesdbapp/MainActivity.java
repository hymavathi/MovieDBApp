package com.udacity.projects.moviesdbapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.projects.moviesdbapp.com.udacity.projects.moviesdbapp.model.Movie;
import com.udacity.projects.moviesdbapp.adapters.MovieRecyclerViewAdapter;
import com.udacity.projects.moviesdbapp.services.AppConstants;
import com.udacity.projects.moviesdbapp.services.MovieService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    MovieRecyclerViewAdapter adapter;
    private static final int NUMBER_OF_COLUMNS = 2;
    RecyclerView recyclerView;
    MovieRecyclerViewAdapter.ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // set up the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.moviesrview);
        new MovieAsyncTask().execute();

    }

    @Override
    public void onItemClick(View view, int position) {

        Intent detailsIntent = new Intent(this, DetailItemActivity.class);

        startActivity(detailsIntent);
    }


    public class MovieAsyncTask extends AsyncTask<Void , Void , Object> {

        @Override
        protected Object doInBackground(Void... voids) {
            return MovieService.getMovies();
        }

        @Override
        protected void onPostExecute(final Object data) {
            adapter = new MovieRecyclerViewAdapter(MainActivity.this, (List<Movie>) data, new MovieRecyclerViewAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent detailsIntent = new Intent(MainActivity.this , DetailItemActivity.class);
                    Movie movie =  ((List<Movie>) data).get(position);
                   //Bundle bundle = new Bundle();
                   // bundle.putParcelable(AppConstants.MOVIE_INTENT_KEY , (Parcelable)movie);
                    detailsIntent.putExtra(AppConstants.MOVIE_INTENT_KEY  , movie);
                    startActivity(detailsIntent);                }
            });

            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this
                    , NUMBER_OF_COLUMNS));
            recyclerView.setAdapter(adapter);

        }
    }
}
