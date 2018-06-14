package com.udacity.projects.moviesdbapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class FavoriteMovieContract {

    public static final String CONTENT_AUTHORITY = "com.udacity.examples.popularmovie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //making constructor private so that can not instantiated
    private FavoriteMovieContract() {
    }

    /* Inner class that defines the table contents */
    public static class FavMovieEntry implements BaseColumns {
        public static final String PATH_MOVIES = "movies";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "favMovie";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_POSTER_PATH = "posterPath";
        public static final String COLUMN_MOVIE_BACK_DROP_PATH = "backdropPath";
        public static final String COLUMN_MOVIE_RATE = "rate";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
    }

    public static final class VideoEntry implements BaseColumns {

        public static final String PATH_VIDEOS = "videos";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_VIDEOS).build();

        public static final String TABLE_NAME = "video";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_VIDEO_NAME = "name";
        public static final String COLUMN_VIDEO_KEY = "key";
        public static final String COLUMN_VIDEO_TYPE = "type";
    }

    public static final class ReviewEntry implements BaseColumns {
        public static final String PATH_REVIEWS = "reviews";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS).build();

        public static final String TABLE_NAME = "review";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_REVIEW_AUTHOR = "author";
        public static final String COLUMN_REVIEW_CONTENT = "content";
    }

}
