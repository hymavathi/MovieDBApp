package com.udacity.projects.moviesdbapp.services;

import android.net.Uri;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udacity.projects.moviesdbapp.adapters.Movie;
import com.udacity.projects.moviesdbapp.adapters.MoviesRestResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieService {

    static final String MOVIEDB_API_URL = "https://image.tmdb.org/t/p/";

   static final String MOVIEDB_API_KEY = "APIKEY";

    static final String MOVIE_CONFIG_URL = "http://api.themoviedb.org/3/movie/popular";

    static final String sort = "";


    public static String getresponseFromMovieDBURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            int code = urlConnection.getResponseCode();
            if (urlConnection.getResponseCode() != 200) {
                return null;
            }
            InputStream in = urlConnection.getInputStream();
            Scanner scan = new Scanner(in);
            scan.useDelimiter("\\A");
            boolean is = scan.hasNext();
            return scan.hasNext() ? scan.next() : null;

        } finally {
            urlConnection.disconnect();
        }

    }

    public static List<Movie> getMovies() {
        Gson gson = new GsonBuilder().create();
        MoviesRestResponse jsonResponse = new MoviesRestResponse();
        try {
            String response = getresponseFromMovieDBURL(buildUrl());
            jsonResponse = gson.fromJson(response, MoviesRestResponse.class);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return jsonResponse.getResults() != null ? jsonResponse.getResults() : null;
    }

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(MOVIE_CONFIG_URL).buildUpon()
                .appendQueryParameter("api_key", MOVIEDB_API_KEY)
                .appendQueryParameter("page", MOVIEDB_API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

//    @Override
//    protected MoviesRestResponse doInBackground(String... url) {
//
//        Gson gson = new GsonBuilder().create();
//        MoviesRestResponse jsonResponse = new MoviesRestResponse();
//        try {
//            HttpURLConnection urlConnection = (HttpURLConnection) buildUrl().openConnection();
//            int code = urlConnection.getResponseCode();
//            if (urlConnection.getResponseCode() != 200) {
//                return null;
//            }
//            InputStream in = urlConnection.getInputStream();
//            Scanner scan = new Scanner(in);
//            scan.useDelimiter("\\A");
//            boolean is = scan.hasNext();
//            jsonResponse = gson.fromJson(scan.next(), MoviesRestResponse.class);
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return jsonResponse;
//    }
}
