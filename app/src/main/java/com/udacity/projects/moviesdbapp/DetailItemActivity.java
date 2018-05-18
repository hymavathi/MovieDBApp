package com.udacity.projects.moviesdbapp;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.projects.moviesdbapp.model.Movie;
import com.udacity.projects.moviesdbapp.util.AppConstants;
import com.udacity.projects.moviesdbapp.util.MovieUtil;

public class DetailItemActivity extends AppCompatActivity {

    private Movie movie;
    public  static  String INTENT_MOVIE_KEY = "MOVIE_KEY";
    private static final String TAG = DetailItemActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
      //  Bundle bundle = this.getIntent().getExtras();
        if ((intent == null) && !intent.hasExtra(AppConstants.MOVIE_INTENT_KEY)) {
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


    }
}
