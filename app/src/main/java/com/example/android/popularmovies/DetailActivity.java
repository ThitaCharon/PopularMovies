package com.example.android.popularmovies;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Model.Movie;
import com.example.android.popularmovies.Model.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        TextView title = findViewById(R.id.tv_detialActivity_Title);
        TextView rating = findViewById(R.id.tv_detialActivity_Rating);
        TextView dateRelease = findViewById(R.id.tv_detialActivity_DateRelease);
        TextView desc =  findViewById(R.id.tv_detialActivity_desc);
        ImageView poster = findViewById(R.id.tv_detialActivity_posterimage);
        final String tag = DetailActivity.class.getSimpleName();

        Intent intent = getIntent();
        final Movie mInfo = intent.getParcelableExtra("mInfo");
        Log.d(tag +" title ", mInfo.getTitle() + "");


        title.setText(mInfo.getTitle());
        rating.setText(mInfo.getRating());
        Picasso.get().load("http://image.tmdb.org/t/p/original/" + mInfo.getPosterUrl()).into(poster);
        dateRelease.setText(mInfo.getDateRelease());
        desc.setText(mInfo.getOverview());

        /**
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String posterurl = ( extras.getString(String.valueOf(R.string.INFO_POSTERLINK)));
            Picasso.get().load("http://image.tmdb.org/t/p/original/" + posterurl).into(poster);
            title.setText((String) extras.get(String.valueOf(R.string.INFO_TITLE)));
            rating.setText((String) extras.get(String.valueOf(R.string.INFO_RATING)));
            dateRelease.setText( extras.getString(String.valueOf(R.string.INFO_DATERELEASE)));
            desc.setText( extras.getString(String.valueOf(R.string.INFO_DESCRIPTION)));
        }
        **/
    }
}
