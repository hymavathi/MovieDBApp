package com.udacity.projects.moviesdbapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.udacity.projects.moviesdbapp.model.Movie;
import com.udacity.projects.moviesdbapp.model.Video;
import com.udacity.projects.moviesdbapp.util.AppConstants;

public class VideoActivity extends AppCompatActivity {

    private Movie movie;
    public  static  String INTENT_MOVIE_KEY = "MOVIE_KEY";
    private static final String RV_POSITION_KEY = "RV_KEY";


    private Parcelable rvSavedState;
    private static final String TAG = VideoActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if ((intent == null) || !intent.hasExtra(AppConstants.MOVIE_INTENT_KEY)) {
            finish();
            return;
        }

        movie = intent.getParcelableExtra(INTENT_MOVIE_KEY);
        setContentView(R.layout.activity_video);
        TextView videoTitle = findViewById(R.id.tv_video);

    }
}
