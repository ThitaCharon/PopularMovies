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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{

    private List<Movie> movieList;
    private Context mContext;
    private static final String IMAGE_URL_PATH = "http://image.tmdb.org/t/p/w185/";

    //MoviesAdapter constructor
    public MoviesAdapter (List<Movie> movieslist, Context context){
        this.movieList = movieslist;
        mContext = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        public TextView title;
//        public TextView rating;
        public ImageView image;

        // MyViewHolder constructor
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
//            title = (TextView) itemView.findViewById(R.id.tv_title);
//            rating = (TextView) itemView.findViewById(R.id.tv_rating);

        }

        @Override
        public void onClick(View itemView) {
            int adapterPosition = getAdapterPosition();
//            String mTitle = movieList.get(adapterPosition).getTitle();
//            Log.d("Onclick display title", mTitle);
        Intent intent = new Intent(itemView.getContext(),DetailActivity.class);
        intent.putExtra(String.valueOf(R.string.INFO_TITLE), movieList.get(adapterPosition).getTitle());
        MoviesAdapter.this.mContext.startActivity(intent);



        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate movie list item o
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_poster, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(IMAGE_URL_PATH + movieList.get(position).getPosterUrl()).into(holder.image);
//        holder.title.setText(movieList.get(position).getTitle());
//        holder.rating.setText(movieList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        }
        return 0;
    }


}
