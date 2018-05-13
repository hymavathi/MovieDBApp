package com.udacity.projects.moviesdbapp.services;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.udacity.projects.moviesdbapp.com.udacity.projects.moviesdbapp.model.Movie;
import com.udacity.projects.moviesdbapp.com.udacity.projects.moviesdbapp.model.MoviesRestResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class MovieService {

   private static final String MOVIEDB_API_URL = "https://image.tmdb.org/t/p/";

    private static final String MOVIEDB_API_KEY = "";

    private static final String MOVIE_CONFIG_URL = "http://api.themoviedb.org/3/movie/popular";

    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/w780/";


    static final String sort = "";


    //Fetch movies
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

    //Load images with picasso
    public static void loadImage(Context context, String imgPath, ImageView imgView) {
        Picasso.with(context).load(IMG_BASE_URL + imgPath).into(imgView);
    }


    private static String getresponseFromMovieDBURL(URL url) throws IOException {
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



    private static URL buildUrl() {
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
}
