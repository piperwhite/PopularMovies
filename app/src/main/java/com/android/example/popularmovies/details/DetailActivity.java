package com.android.example.popularmovies.details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.android.example.popularmovies.Movie;
import com.android.example.popularmovies.MoviesApi;
import com.android.example.popularmovies.R;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        if(i.hasExtra("id")){
            Toast.makeText(this, i.getStringExtra("id"), Toast.LENGTH_LONG).show();
            new DetailMovieAsyntask().execute(i.getStringExtra("id")); //TODO Constant
        }

    }

    public class DetailMovieAsyntask extends AsyncTask<String, Void, Movie>{

        @Override
        protected Movie doInBackground(String... ids) {
            try {
                Movie movie = MoviesApi.getMovieDetails(ids[0]);
                return movie;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            System.out.println(movie.getImage());
        }
    }
}
