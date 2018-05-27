package com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{

    private List<Movie> movieList;
    private Context contex;


    //MoviesAdapter constructor
    public MoviesAdapter (List<Movie> movieslist, Context context){
        this.movieList = movieslist;
        this.contex = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView rating;
        public ImageView image;

        // MyViewHolder constructor
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            rating = (TextView) itemView.findViewById(R.id.tv_rating);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate movie list item o
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //if call Picasso the cause Error
//        Picasso.with(contex).load(movieList.get(position).getPosterUrl()).resize(50,50).placeholder(R.color.title).into(holder.image);
        holder.title.setText(movieList.get(position).getTitle());
        holder.rating.setText(movieList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        }
        return 0;
    }


}
