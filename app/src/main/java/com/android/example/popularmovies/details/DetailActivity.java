package com.android.example.popularmovies.details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.example.popularmovies.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        if(i.hasExtra("id")){
            Toast.makeText(this, i.getStringExtra("id"), Toast.LENGTH_LONG).show(); //TODO Constant
        }

    }
}
