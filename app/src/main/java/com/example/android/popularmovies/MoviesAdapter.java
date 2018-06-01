package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{

    public List<Movie> movieList;
    private Context mContext;
    public static final String IMAGE_URL_PATH = "http://image.tmdb.org/t/p/w185/";

    //MoviesAdapter constructor
    public MoviesAdapter (List<Movie> movieslist, Context mContext){
        this.movieList = movieslist;
        this.mContext = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView image;

        // MyViewHolder constructor
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            int adapterPosition = getAdapterPosition();
            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
            intent.putExtra(String.valueOf(R.string.INFO_TITLE), movieList.get(adapterPosition).getTitle());
            intent.putExtra(String.valueOf(R.string.INFO_RATING), movieList.get(adapterPosition).getRating());
            intent.putExtra(String.valueOf(R.string.INFO_DATERELEASE), movieList.get(adapterPosition).getDateRelease());
            intent.putExtra(String.valueOf(R.string.INFO_DESCRIPTION), movieList.get(adapterPosition).getOverview());
            intent.putExtra(String.valueOf(R.string.INFO_POSTERLINK), movieList.get(adapterPosition).getPosterUrl());
            try {
                MoviesAdapter.this.mContext.startActivity(intent);
            } catch (RuntimeException e) {
                Log.d(MoviesAdapter.class.getSimpleName(), e.getMessage());
            }
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate movie list item o
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_poster, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(IMAGE_URL_PATH + movieList.get(position).getPosterUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        }
        return 0;
    }


}
