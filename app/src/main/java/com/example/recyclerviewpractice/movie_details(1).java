package com.example.recyclerviewpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class movie_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
       // getIncomingIntent();
        TextView movieTitle = findViewById(R.id.movie_title);
        TextView releaseDate = findViewById(R.id.releaseDateData);
        TextView movieDescription = findViewById(R.id.movie_description);
        ImageView moviePoster = findViewById(R.id.movie_poster);
        ImageView movieCover = findViewById(R.id.movie_cover);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        releaseDate.setText(getIntent().getExtras().getString("release_date"));
        ratingBar.setRating((float) Double.parseDouble(getIntent().getExtras().getString("vote_average")));
        movieTitle.setText(getIntent().getExtras().getString("original_title"));
        movieDescription.setText(getIntent().getExtras().getString("overview"));
        Glide.with(this)
                .load(getIntent().getExtras().getString("poster_path")).placeholder(R.drawable.ic_launcher_foreground)
                .into(moviePoster);
        Glide.with(this).load(getIntent().getExtras().getString("poster_path")).placeholder(R.drawable.ic_launcher_foreground).into(movieCover);
    }

    private void getIncomingIntent() {
        TextView movieTitle, movieDetail;
        ImageView moviePhoto, movieCover;
        moviePhoto = findViewById(R.id.poster);
        movieCover = findViewById(R.id.movie_cover);
        movieTitle = findViewById(R.id.title);
        ///ratingBarDetails = findViewById(R.id.ratingBar);
        movieDetail = findViewById(R.id.movie_description);

        Intent startedActivityIntent = getIntent();
        String movieThumbnail = getIntent().getExtras().getString("poster_path");
        String movieName = getIntent().getExtras().getString("original_title");
        String movieOverview = getIntent().getExtras().getString("overview");
        //String rating = getIntent().getExtras().getString("vote_average");
        //String dateOfRelease = getIntent().getExtras().getString("release_date");

            /*Glide.with(MainActivity.this)
                    .load("https://image.tmdb.org/t/p/w500"+movieList.get(position).getPosterPath())
                    .into(holder.flag);*/

           /* Glide.with(this)
                    .load(movieThumbnail)
                    .placeholder(R.drawable.ic_launcher_background).into(moviePhoto);*/

        movieTitle.setText(movieName);
        movieDetail.setText(movieOverview);
    }
}