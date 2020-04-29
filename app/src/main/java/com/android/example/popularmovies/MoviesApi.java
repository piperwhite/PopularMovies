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
    public final static String IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/w185";
    private final static String POPULAR_MOVIES = "/movie/popular";
    private final static String TOP_RATED = "/movie/top_rated";
    private final static String MOVIE_DETAILS= "/movie/";
    private static final String RESULTS = "results";
    private static final String POSTER_PATH = "poster_path";
    private static final String ID = "id";
    private static final String OVERVIEW = "overview";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";

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
                System.out.println(jsonString);
                List<Movie> movies = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject currentJSONObject = jsonArray.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setImage(currentJSONObject.getString(POSTER_PATH));
                    movie.setId(currentJSONObject.getString(ID));
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


    public static Movie getMovieDetails(String id) throws IOException {
        URL url = buildUrl(MOVIE_DETAILS + id);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = urlConnection.getInputStream();

        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");

        boolean hasInput = scanner.hasNext();
        if (hasInput) {
            String jsonString = scanner.next();
            System.out.println(jsonString);
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                Movie movie = new Movie();
                setMovieData(movie, jsonObject);
                return movie;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private static void setMovieData(Movie movie, JSONObject jsonObject) throws JSONException {
        movie.setImage(jsonObject.getString(POSTER_PATH));
        movie.setId(jsonObject.getString(ID));
        movie.setOverview(jsonObject.getString(OVERVIEW));
        movie.setTitle(jsonObject.getString(ORIGINAL_TITLE));
        movie.setReleaseDate(jsonObject.getString(RELEASE_DATE));
        movie.setVoteAverage(jsonObject.getString(VOTE_AVERAGE));
    }


}
