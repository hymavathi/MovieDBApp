package com.udacity.projects.moviesdbapp;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.udacity.projects.moviesdbapp.adapters.Movie;
import com.udacity.projects.moviesdbapp.adapters.MovieRecyclerViewAdapter;
import com.udacity.projects.moviesdbapp.services.MovieService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.ItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    MovieRecyclerViewAdapter adapter;
    private static final int NUMBER_OF_COLUMNS = 3;
    RecyclerView recyclerView;

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
    }


    public class MovieAsyncTask extends AsyncTask<Void , Void , Object> {

        @Override
        protected Object doInBackground(Void... voids) {
            return MovieService.getMovies();
        }

        @Override
        protected void onPostExecute(Object data) {
            adapter = new MovieRecyclerViewAdapter(MainActivity.this,(List<Movie>) data);

            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this
                    , NUMBER_OF_COLUMNS));
            recyclerView.setAdapter(adapter);

        }
    }
}
