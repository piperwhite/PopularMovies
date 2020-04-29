package com.android.example.popularmovies.details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.example.popularmovies.Movie;
import com.android.example.popularmovies.MoviesApi;
import com.android.example.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "id";

    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvOverview;
    private TextView tvReleaseDate;
    private TextView tvAverageVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivPoster = findViewById(R.id.iv_poster);
        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvAverageVote = findViewById(R.id.tv_average_vote);
        tvReleaseDate = findViewById(R.id.tv_release_date);

        Intent i = getIntent();
        if(i.hasExtra(EXTRA_ID)){
            new DetailMovieAsyntask().execute(i.getStringExtra(EXTRA_ID));
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
            loadMovieData(movie);
        }
    }

    private void loadMovieData(Movie movie) {
        tvTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvAverageVote.setText(movie.getVoteAverage());
        tvOverview.setText(movie.getOverview());
        Picasso.get().load(MoviesApi.IMAGE_BASE_PATH + movie.getImage()).error(R.drawable.ic_broken_image_24px).into(ivPoster);
    }
}
