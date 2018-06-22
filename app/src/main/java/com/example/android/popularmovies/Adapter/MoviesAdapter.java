package com.example.android.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.android.popularmovies.DetailActivity;
import com.example.android.popularmovies.Model.Movie;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Callback;
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

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate movie list item o
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_poster, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Picasso.get().load(IMAGE_URL_PATH + movieList.get(position).getPoster_path())
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        }
        return 0;
    }

    public List<Movie> getMovieList(){
        return movieList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView image;
        public ProgressBar progressBar;

        // MyViewHolder constructor
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progressbar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            int adapterPosition = getAdapterPosition();
            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
//            intent.putExtra(String.valueOf(R.string.INFO_TITLE), movieList.get(adapterPosition).getTitle());
//            intent.putExtra(String.valueOf(R.string.INFO_RATING), movieList.get(adapterPosition).getRating());
//            intent.putExtra(String.valueOf(R.string.INFO_DATERELEASE), movieList.get(adapterPosition).getDateRelease());
//            intent.putExtra(String.valueOf(R.string.INFO_DESCRIPTION), movieList.get(adapterPosition).getOverview());
//            intent.putExtra(String.valueOf(R.string.INFO_POSTERLINK), movieList.get(adapterPosition).getPosterUrl());

        // Using Parcelable
            final Movie movie = movieList.get(adapterPosition);
            intent.putExtra("mSelected", movie);
//                Log.d (MoviesAdapter.class.getSimpleName() , movie.getTitle());
//                Log.d (MoviesAdapter.class.getSimpleName() , movie.getOverview());

            try {
                MoviesAdapter.this.mContext.startActivity(intent);
            } catch (RuntimeException e) {
                Log.d(MoviesAdapter.class.getSimpleName(), e.getMessage());
            }
        }
    }
}
