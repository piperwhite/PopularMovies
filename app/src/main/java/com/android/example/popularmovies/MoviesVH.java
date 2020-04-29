package com.android.example.popularmovies;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.popularmovies.details.DetailActivity;
import com.squareup.picasso.Picasso;

public class MoviesVH extends RecyclerView.ViewHolder {

    private ImageView ivPoster;

    public MoviesVH(@NonNull View itemView) {
        super(itemView);
        ivPoster = itemView.findViewById(R.id.iv_poster);
    }

    public void setImage(String url){
        Picasso.get().load(MoviesApi.IMAGE_BASE_PATH + url).error(R.drawable.ic_broken_image_24px).into(ivPoster);
    }

    public void setOnClickListener(final String id) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(itemView.getContext(), DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_ID, id);
                itemView.getContext().startActivity(i);
            }
        });
    }
}
