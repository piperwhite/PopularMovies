package com.android.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String LAST_VISIBLE_POSITION = "lastVisiblePosition";
    private static final String IS_POPULAR_SORT = "isPopularSort";
    private boolean isPopularMoviesSort = true;
    private int lastVisiblePosition = 0;
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
        if(savedInstanceState != null){
            lastVisiblePosition = savedInstanceState.getInt(LAST_VISIBLE_POSITION);
            isPopularMoviesSort = savedInstanceState.getBoolean(IS_POPULAR_SORT);
        }
        loadMovies();
    }

    private void loadMovies(){
        new MoviesAsyncTask(isPopularMoviesSort).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LAST_VISIBLE_POSITION, ((GridLayoutManager) rvMovies.getLayoutManager()).findLastVisibleItemPosition());
        outState.putBoolean(IS_POPULAR_SORT, isPopularMoviesSort);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_sort_popular){
            isPopularMoviesSort = true;
            lastVisiblePosition = 0;
            loadMovies();
            return true;
        }
        if(itemId == R.id.action_sort_top_rated){
            isPopularMoviesSort = false;
            lastVisiblePosition = 0;
            loadMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MoviesAsyncTask extends AsyncTask<Void, Void, List<Movie>> {
        private boolean isPopular = true;

        public MoviesAsyncTask(boolean isPopular){
            this.isPopular = isPopular;
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            try {
                if(isPopular){
                    return MoviesApi.getPopularMovies();
                }else{
                    return MoviesApi.getTopRatedMovies();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if(movies != null){
                adapter.setMovies(movies);
                rvMovies.scrollToPosition(lastVisiblePosition);
            }
        }
    }
}
