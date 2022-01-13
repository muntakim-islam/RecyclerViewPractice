package com.example.recyclerviewpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.Model;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    final OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String response = null;
        try {
            response = run("https://api.themoviedb.org/3/movie/popular?api_key=3fa9058382669f72dcb18fb405b7a831&language=en-US&page=1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        MovieRespospone mr = new Gson().fromJson(response, MovieRespospone.class);

        RecyclerView countryRecyclerView = findViewById(R.id.my_list);
        countryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        countryRecyclerView.setAdapter(new MyAdapter(mr.getResults()));
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<Result> movieList;
        private Context mContext;
        public MyAdapter(List<Result> movieList) { this.movieList = movieList; }

        @Override
        public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

            View mItemView = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.list_item_country, parent, false);
            return new MyViewHolder(mItemView);
        }

        @Override
        public void onBindViewHolder( MainActivity.MyViewHolder holder, int position) {

            holder.name.setText(movieList.get(position).getTitle());

            Glide.with(MainActivity.this)
                    .load("https://image.tmdb.org/t/p/w500"+movieList.get(position).getPosterPath())
                    .into(holder.flag);
            holder.rating.setText(String.valueOf(movieList.get(position).getVoteAverage()));
            //holder.overview.setText(String.valueOf(movieList.get(position).getOverview()));
            holder.releaseDate.setText(String.valueOf(movieList.get(position).getReleaseDate()));

            holder.ParentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    Result clickDataItem = movieList.get(pos);
                    Intent intent = new Intent(MainActivity.this, movie_details.class);
                    intent.putExtra("original_title",  movieList.get(pos).getOriginalTitle());
                    intent.putExtra("poster_path", movieList.get(pos).getPosterPath());
                    intent.putExtra("overview", movieList.get(pos).getOverview());
                    intent.putExtra("vote_average", Double.toString(movieList.get(pos).getVoteAverage()));
                    intent.putExtra("release_date", movieList.get(pos).getReleaseDate());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, rating, overview, releaseDate;
        ImageView flag;
        RelativeLayout ParentLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            //overview = itemView.findViewById(R.id.overview);
            rating = itemView.findViewById(R.id.rating);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            flag = itemView.findViewById(R.id.poster);
            ParentLayout = itemView.findViewById(R.id.parent_layout);

            /*itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        //Result clickDataItem = movieList.get(pos);
                        Intent intent = new Intent(MainActivity.this, movie_details.class);
                        /*intent.putExtra("original_title", movieList.get(pos).getOriginalTitle());
                        intent.putExtra("poster_path", movieList.get(pos).getPosterPath());
                        intent.putExtra("overview", movieList.get(pos).getOverview());
                        intent.putExtra("vote_average", Double.toString(movieList.get(pos).getVoteAverage()));
                        intent.putExtra("release_date", movieList.get(pos).getReleaseDate());*/
                        /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Toast.makeText(v.getContext(), "You clicked " , Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                       // Toast.makeText(v.getContext(), "You clicked " + clickDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }

}