package com.udacity.projects.moviesdbapp.util;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.udacity.projects.moviesdbapp.data.FavoriteMovieContract;
import com.udacity.projects.moviesdbapp.model.Movie;
import com.udacity.projects.moviesdbapp.model.Review;
import com.udacity.projects.moviesdbapp.model.Video;

import java.util.List;

public class ContentProviderUtil {

    private static final String TAG = ContentProviderUtil.class.getSimpleName();

    public static Cursor getFavoriteMovies(Context context) {
        return context.getContentResolver().query(FavoriteMovieContract.FavMovieEntry.CONTENT_URI , null , null , null, null);

    }
    public static Cursor getVideos(Context context, Movie movie) {
        Uri uri = FavoriteMovieContract.VideoEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movie.getId())).build();
        return context.getContentResolver().query(uri, null, null, null, null);
    }

    public static Cursor getReviews(Context context, Movie movie) {
        Uri uri = FavoriteMovieContract.ReviewEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movie.getId())).build();
        return context.getContentResolver().query(uri, null, null, null, null);
    }

    public static void addFavoriteMovie(Context context , Movie movie) {
        Log.d(TAG, "addFavoriteMovie: Started ,Movie =" + movie);
        ContentValues mValue = new ContentValues();
        mValue.put(FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_TITLE , movie.getTitle());
        mValue.put(FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_ID, movie.getId());
        mValue.put(FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_POSTER_PATH, movie.getPosterPath());
        mValue.put(FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_BACK_DROP_PATH, movie.getBackdropPath());
        mValue.put(FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_RATE, movie.getVoteAverage());
        mValue.put(FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        mValue.put(FavoriteMovieContract.FavMovieEntry.COLUMN_MOVIE_OVERVIEW, movie.getOverview());

        Uri movieUri = context.getContentResolver().insert(FavoriteMovieContract.FavMovieEntry.CONTENT_URI , mValue);
        long movieId = ContentUris.parseId(movieUri);
        Log.d(TAG, "addFavoriteMovie: Movie Id is " +  movieId);

    }

    public static void addVideo(Context context , int movieId , List<Video> videos) {
        Log.d(TAG, "addVideo: ");
        if (videos == null ){
            return ;
        }

        for (Video video : videos) {
            ContentValues vValue = new ContentValues();
            vValue.put(FavoriteMovieContract.VideoEntry.COLUMN_MOVIE_ID, movieId);
            vValue.put(FavoriteMovieContract.VideoEntry.COLUMN_VIDEO_NAME, video.getName());
            vValue.put(FavoriteMovieContract.VideoEntry.COLUMN_VIDEO_KEY, video.getKey());
            vValue.put(FavoriteMovieContract.VideoEntry.COLUMN_VIDEO_TYPE, video.getType());

           context.getContentResolver().insert(FavoriteMovieContract.VideoEntry.CONTENT_URI, vValue);

        }

    }
    public static void addReviews(Context context, int movieId, List<Review> reviews) {

        if (reviews == null)
            return;

        for (Review review: reviews) {
            ContentValues rValue = new ContentValues();
            rValue.put(FavoriteMovieContract.ReviewEntry.COLUMN_MOVIE_ID, movieId);
            rValue.put(FavoriteMovieContract.ReviewEntry.COLUMN_REVIEW_AUTHOR, review.getAuthor());
            rValue.put(FavoriteMovieContract.ReviewEntry.COLUMN_REVIEW_CONTENT, review.getContent());

            context.getContentResolver().insert(FavoriteMovieContract.ReviewEntry.CONTENT_URI, rValue);
        }
    }
    public static void removeFavoriteMovie(Context context, Movie movie) {
        Uri movieWithIdUri = FavoriteMovieContract.FavMovieEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movie.getId())).build();

        context.getContentResolver().delete(movieWithIdUri, null, null);
    }

    public static boolean isFavorite(Context context, Movie movie) {

        Uri movieWithIdUri = FavoriteMovieContract.FavMovieEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movie.getId())).build();

        Cursor cursor = context.getContentResolver().query(movieWithIdUri, null, null, null, null);

        return  (cursor != null && cursor.getCount() == 1);
    }
}
