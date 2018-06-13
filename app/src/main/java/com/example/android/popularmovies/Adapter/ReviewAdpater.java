package com.example.android.popularmovies.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Model.Review;
import com.example.android.popularmovies.R;

import java.util.List;

public class ReviewAdpater extends RecyclerView.Adapter<ReviewAdpater.ViewHolder> {

    public List<Review> reviewList;
    private Context rContent;

    public ReviewAdpater(List<Review> reviewList, Context rContent){
        this.reviewList = reviewList;
        this.rContent = rContent;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_review , parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());
        holder.review_url.setText(review.getUrl());
    }

    @Override
    public int getItemCount() {
        if (reviewList != null){
            return reviewList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView author;
        public TextView content;
        public TextView review_url;

        public ViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.tv_author);
            content = (TextView) itemView.findViewById(R.id.tv_review_content);
            review_url = (TextView) itemView.findViewById(R.id.tv_review_url);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Toast toast = Toast.makeText(rContent ,"Show Review URL"+review_url, Toast.LENGTH_LONG);
            toast.show();
        }
    }

}
