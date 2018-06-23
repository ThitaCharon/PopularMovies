package com.example.android.popularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Model.Video;
import com.example.android.popularmovies.R;

import java.util.List;

public class VideoAdpater extends RecyclerView.Adapter<VideoAdpater.ViewHolder> {
    List<Video> videoList;
    Context vContext;

    public VideoAdpater(List<Video> videoList, Context vContext) {
        this.videoList = videoList;
        this.vContext = vContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_trailer,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Video trailer = videoList.get(position);
        holder.name.setText(trailer.getName());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent watchTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getUrl()));
                vContext.startActivity(watchTrailer);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (videoList != null){ return videoList.size(); }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_trailer_name);
            itemView.setOnClickListener(this);

        }
        private void setClickListener(ItemClickListener itemClickListener){
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view,getAdapterPosition());
        }
    }
}
