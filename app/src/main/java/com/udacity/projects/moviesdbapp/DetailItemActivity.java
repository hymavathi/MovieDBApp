package com.udacity.projects.moviesdbapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.projects.moviesdbapp.com.udacity.projects.moviesdbapp.model.Movie;
import com.udacity.projects.moviesdbapp.services.AppConstants;
import com.udacity.projects.moviesdbapp.services.MovieService;

public class DetailItemActivity extends AppCompatActivity {

    private Movie movie;
    public  static  String INTENT_MOVIE_KEY = "MOVIE_KEY";
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
        voteTextView.setText(String.valueOf(movie.getVoteAverage()));
        plotTextView.setText(movie.getOverview());

        MovieService.loadImage(this, movie.getBackdropPath() , posterImageView);


    }
}