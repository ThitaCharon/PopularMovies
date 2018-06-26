package com.example.android.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.DetailActivity;
import com.example.android.popularmovies.Model.Review;
import com.example.android.popularmovies.R;

import java.util.List;

public class ReviewAdpater extends RecyclerView.Adapter<ReviewAdpater.ViewHolder> {

    public List<Review> reviewList;
    private Context rContext;

    public ReviewAdpater(List<Review> reviewList, Context rContext){
        this.reviewList = reviewList;
        this.rContext = rContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_review , parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Review reviewSelected = reviewList.get(position);
        holder.author.setText(reviewSelected.getAuthor());
        holder.setClickListener(new ItemClickListener() {    // Bind the listener
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(rContext, DetailActivity.class);
                if (holder.content.getVisibility() == View.GONE)
                {
                    holder.content.setText(reviewList.get(position).getContent());
                    holder.content.setVisibility(View.VISIBLE);
                }else if (holder.content.getVisibility() == View.VISIBLE){
                    holder.content.setVisibility(View.GONE);
                }

                Log.d("Click on Review ",rContext.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (reviewList != null){ return reviewList.size(); }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView author;
        private TextView content;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.tv_author);
            content = (TextView) itemView.findViewById(R.id.review_content);
            itemView.setOnClickListener(this);
        }

        private void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }

    }

}
