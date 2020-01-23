package com.android.example.popularmovies;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class MoviesVH extends RecyclerView.ViewHolder {

    private ImageView ivPoster;

    public MoviesVH(@NonNull View itemView) {
        super(itemView);
        ivPoster = itemView.findViewById(R.id.iv_poster);
    }

    public void setImage(String url){
        Picasso.get().load(url).into(ivPoster); //TODO handle errors network and placeholder
    }

}
