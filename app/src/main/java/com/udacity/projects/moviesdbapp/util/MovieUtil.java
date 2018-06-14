package com.udacity.projects.moviesdbapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.udacity.projects.moviesdbapp.model.Movie;
import com.udacity.projects.moviesdbapp.model.MoviesRestResponse;
import com.udacity.projects.moviesdbapp.model.Review;
import com.udacity.projects.moviesdbapp.model.ReviewRestResponse;
import com.udacity.projects.moviesdbapp.model.Video;
import com.udacity.projects.moviesdbapp.model.VideoRestResponse;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class MovieUtil {



    public static List<Movie> getPopularMovies() {
            return getMovies(AppConstants.POPULAR_MOVIE_URL);

    }

    public static List<Movie> getTopRatedMovies() {
        return getMovies(AppConstants.TOP_RATED_MOVIE_URL);
    }


    public static List<Movie> getFavoriteMovies() {
        return getMovies(AppConstants.TOP_RATED_MOVIE_URL);
    }
    public static List<Video> loadVideos(long id)  {
        return getVideos(AppConstants.MOVIE_BASE_URL + id + AppConstants.MOVIE_VIDEOS_URL_POSTFIX);
    }

    public static List<Review> loadReviews(long id) {
        return getReviews(AppConstants.MOVIE_BASE_URL + id + AppConstants.MOVIE_REVIEWS_URL_POSTFIX);
    }

    //Load images with picasso
    public static void loadImage(Context context, String imgPath, ImageView imgView) {
        Picasso.with(context).load(AppConstants.IMG_BASE_URL + imgPath).into(imgView);
    }

    //check if network is active or not
    public static boolean isNetworkActive(Context context) {
        ConnectivityManager cMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cMgr == null) {
            return false;

        }
        NetworkInfo network = cMgr.getActiveNetworkInfo();

        return network != null && network.isConnected();

    }


    private static String getresponseFromMovieDBURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            if (urlConnection.getResponseCode() != 200) {
                return null;
            }
            InputStream in = urlConnection.getInputStream();
            Scanner scan = new Scanner(in);
            scan.useDelimiter("\\A");
            return scan.hasNext() ? scan.next() : null;

        } finally {
            urlConnection.disconnect();
        }

    }


    private static URL buildUrl(String urlString) {
        Uri builtUri = Uri.parse(urlString).buildUpon()
                .appendQueryParameter("api_key", AppConstants.MOVIEDB_API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    //Fetch movies by making a call to MovieAPI
    private static List<Movie> getMovies(String url) {
        Gson gson = new GsonBuilder().create();
        MoviesRestResponse jsonResponse = new MoviesRestResponse();
        try {
            String response = getresponseFromMovieDBURL(buildUrl(url));
            jsonResponse = gson.fromJson(response, MoviesRestResponse.class);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return jsonResponse.getResults();
    }

    //Fetch movies by making a call to MovieAPI
    private static List<Video> getVideos(String url) {
        Gson gson = new GsonBuilder().create();
        VideoRestResponse jsonResponse = new VideoRestResponse();
        try {
            String response = getresponseFromMovieDBURL(buildUrl(url));
            jsonResponse = gson.fromJson(response, VideoRestResponse.class);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return jsonResponse.getResults();
    }

    //Fetch movies by making a call to MovieAPI
    private static List<Review> getReviews(String url) {
        Gson gson = new GsonBuilder().create();
        ReviewRestResponse jsonResponse = new ReviewRestResponse();
        try {
            String response = getresponseFromMovieDBURL(buildUrl(url));
            jsonResponse = gson.fromJson(response, ReviewRestResponse.class);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return jsonResponse.getResults();
    }
}
