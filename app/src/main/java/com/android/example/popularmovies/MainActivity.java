package com.android.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Movie;
import android.os.Bundle;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    private final int COLS = 2;

    private RecyclerView rvMovies;
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovies = findViewById(R.id.rv_movies);

        rvMovies.setLayoutManager(new GridLayoutManager(this, COLS));

        adapter = new MoviesAdapter();
        
        rvMovies.setAdapter(adapter);

        loadMovies();
    }

    private void loadMovies() {
    }
}
