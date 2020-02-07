package com.android.example.popularmovies;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MoviesApi {

    private final static String API_KEY = ""; //TODO add key
    private final static String BASE_URL = "http://api.themoviedb.org/3";
    private final static String POPULAR_MOVIES = "/movie/popular";
    private final static String TOP_RATED = "/movie/top_rated";


    public static URL buildUrl(String path) {
        Uri builtUri = Uri.parse(BASE_URL + path).buildUpon() 
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(MoviesApi.class.getSimpleName(), "Error building URL", e);
        }

        return url;
    }



    public static List<Movie> getPopularMovies() throws IOException {
        return getMovies(buildUrl(POPULAR_MOVIES));
    }
    public static List<Movie> getTopRatedMovies() throws IOException {
        return getMovies(buildUrl(TOP_RATED));
    }


    private static List<Movie> getMovies(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String jsonString  = scanner.next();
                List<Movie> movies = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++){
                    Movie movie = new Movie();
                    movie.setImage(jsonArray.getJSONObject(i).getString("poster_path")); //TODO constant
                    movies.add(movie);
                }
                System.out.println(jsonString);
                return movies;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return null;
    }



}
