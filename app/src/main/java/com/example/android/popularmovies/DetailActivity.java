package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView title, rating, dateRelease, desc;
    private ImageView poster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        title = (TextView) findViewById(R.id.tv_detaitActivity_Title);
        rating = (TextView) findViewById(R.id.tv_detaitActivity_Rating);
        dateRelease = (TextView) findViewById(R.id.tv_detaitActivity_DateRelease);
        desc =  (TextView) findViewById(R.id.tv_detaitActivity_desc);
        poster = (ImageView)findViewById(R.id.tv_detaitActivity_posterimage);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Log.d( "Rating ", (String) extras.get(String.valueOf(R.string.INFO_RATING)));

        if (extras != null) {
            String posterurl = ((String) extras.getString(String.valueOf(R.string.INFO_POSTERLINK)));
            Picasso.get().load("http://image.tmdb.org/t/p/original/" + posterurl).into(poster);
            title.setText((String) extras.get(String.valueOf(R.string.INFO_TITLE)));
            rating.setText((String) extras.get(String.valueOf(R.string.INFO_RATING)));
            dateRelease.setText((String) extras.getString(String.valueOf(R.string.INFO_DATERELEASE)));
            desc.setText((String) extras.getString(String.valueOf(R.string.INFO_DESCRIPTION)));

        }
    }

}
