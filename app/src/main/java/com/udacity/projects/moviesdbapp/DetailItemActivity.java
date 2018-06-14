package com.udacity.projects.moviesdbapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.projects.moviesdbapp.adapters.MovieRecyclerViewAdapter;
import com.udacity.projects.moviesdbapp.adapters.ReviewRecyclerViewAdapter;
import com.udacity.projects.moviesdbapp.adapters.VideoRecyclerViewAdapter;
import com.udacity.projects.moviesdbapp.model.Movie;
import com.udacity.projects.moviesdbapp.model.Review;
import com.udacity.projects.moviesdbapp.model.Video;
import com.udacity.projects.moviesdbapp.util.AppConstants;
import com.udacity.projects.moviesdbapp.util.MovieUtil;
import com.udacity.projects.moviesdbapp.util.SimpleDividerItemDecoration;

import java.util.List;

public class DetailItemActivity extends AppCompatActivity implements VideoRecyclerViewAdapter.ItemClickListener , ReviewRecyclerViewAdapter.ItemClickListener {

    private Movie movie;
    public  static  String INTENT_MOVIE_KEY = "MOVIE_KEY";
    private static final String TAG = DetailItemActivity.class.getSimpleName();
    private VideoRecyclerViewAdapter videoAdapter;
    private ReviewRecyclerViewAdapter reviewAdapter;

    private RecyclerView videoRecyclerView;

    private RecyclerView reviewRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // set up the RecyclerView
        videoRecyclerView = (RecyclerView) findViewById(R.id.rv_videos);
        reviewRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);

        Intent intent = getIntent();
        if ((intent == null) || !intent.hasExtra(AppConstants.MOVIE_INTENT_KEY)) {
            finish();
            return;
        }

        movie = intent.getParcelableExtra(AppConstants.MOVIE_INTENT_KEY);

        TextView titleTextView = findViewById(R.id.title_tv);
        TextView releaseDateTextView = findViewById(R.id.release_date_tv);
        TextView voteTextView = findViewById(R.id.vote_tv);
        TextView plotTextView = findViewById(R.id.plot_tv);
        ImageView posterImageView = findViewById(R.id.image_iv);
        titleTextView.setText(movie.getTitle());
        releaseDateTextView.setText(movie.getReleaseDate());
        voteTextView.setText(String.valueOf(movie.getVoteAverage())+"/10");
        plotTextView.setText(movie.getOverview());
        Log.d(TAG, "title is: " + titleTextView.getText());
        Log.d(TAG, "title is: " + movie.getOriginalTitle());

        MovieUtil.loadImage(this, movie.getBackdropPath() , posterImageView);

        if (MovieUtil.isNetworkActive(this)) {
            VideoAsyncTask task = new VideoAsyncTask(movie.getId());
            task.execute();

            ReviewAsyncTask reviewTask = new ReviewAsyncTask((movie.getId()));
            reviewTask.execute();

        } else {
            Log.e(TAG, "onCreate: Network is not available ");

            Toast.makeText(getBaseContext(), "Network is not available", Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onItemClick(View view, int position) {
        Intent detailsIntent = new Intent(this, VideoActivity.class);

        startActivity(detailsIntent);
    }


    private class VideoAsyncTask extends AsyncTask<Void, Void, Object> {
        long id;
        public VideoAsyncTask(long id) {
            super();
            this.id = id;

        }
        @Override
        protected Object doInBackground(Void... voids) {
            if (MovieUtil.isNetworkActive(DetailItemActivity.this)) {

                     return MovieUtil.loadVideos(this.id);



                }
             else {
                Toast.makeText(getBaseContext(), "Network is not available", Toast.LENGTH_LONG).show();
                return null;
            }

        }

        @Override
        protected void onPostExecute(final Object data) {

            videoAdapter = new VideoRecyclerViewAdapter(DetailItemActivity.this, (List<Video>) data, new VideoRecyclerViewAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent detailsIntent = new Intent(DetailItemActivity.this, VideoActivity.class);
                    Video video = ((List<Video>) data).get(position);

                    detailsIntent.putExtra(AppConstants.MOVIE_INTENT_KEY, video);
                    startActivity(detailsIntent);
                }
            });

            videoRecyclerView.setLayoutManager(new LinearLayoutManager(DetailItemActivity.this));

            videoRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
            videoRecyclerView.setAdapter(videoAdapter);


        }
    }

    private class ReviewAsyncTask extends AsyncTask<Void, Void, Object> {
        long id;
        public ReviewAsyncTask(long id) {
            super();
            this.id = id;

        }
        @Override
        protected Object doInBackground(Void... voids) {
            if (MovieUtil.isNetworkActive(DetailItemActivity.this)) {

                return MovieUtil.loadReviews(id);

            }
            else {
                Toast.makeText(getBaseContext(), "Network is not available", Toast.LENGTH_LONG).show();
                return null;
            }

        }

        @Override
        protected void onPostExecute(final Object data) {

            reviewAdapter = new ReviewRecyclerViewAdapter(DetailItemActivity.this, (List<Review>) data, new ReviewRecyclerViewAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent detailsIntent = new Intent(DetailItemActivity.this, ReviewActivity.class);
                    Review review = ((List<Review>) data).get(position);

                    detailsIntent.putExtra(AppConstants.MOVIE_INTENT_KEY, review);
                    startActivity(detailsIntent);
                }
            });

            reviewRecyclerView.setLayoutManager(new LinearLayoutManager(DetailItemActivity.this));

            reviewRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
            reviewRecyclerView.setAdapter(reviewAdapter);


        }
    }
}
