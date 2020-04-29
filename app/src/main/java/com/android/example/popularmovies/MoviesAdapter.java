package com.android.example.popularmovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesVH> {

    private List<Movie> movies = new ArrayList<>();

    @NonNull
    @Override
    public MoviesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MoviesVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesVH holder, int position) {
        holder.setImage(movies.get(position).getImage());
        holder.setOnClickListener(movies.get(position).getId());
    }

    public void setMovies(List<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
