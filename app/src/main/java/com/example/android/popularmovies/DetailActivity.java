package com.example.android.popularmovies;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Adapter.ReviewAdpater;
import com.example.android.popularmovies.Model.Movie;
import com.example.android.popularmovies.Model.MovieService;
import com.example.android.popularmovies.Model.Review;
import com.example.android.popularmovies.Model.ReviewRespond;
import com.example.android.popularmovies.Model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private List<Review> reviewsList = new ArrayList<>();
    private ReviewAdpater reviewAdapter;
    private RecyclerView rRecycleView;

    private final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        TextView title = findViewById(R.id.tv_detailActivity_Title);
        TextView rating = findViewById(R.id.tv_detailActivity_Rating);
        TextView dateRelease = findViewById(R.id.tv_detailActivity_DateRelease);
        TextView desc =  findViewById(R.id.tv_detailActivity_desc);
        ImageView poster = findViewById(R.id.tv_detailActivity_posterimage);
        Button favBotton = findViewById(R.id.btn_favorite);
        rRecycleView = findViewById(R.id.rv_review);
        rRecycleView.setLayoutManager(new GridLayoutManager(this,2));
        rRecycleView.setHasFixedSize(true);

        Intent intent = getIntent();
        final Movie mSelected = intent.getParcelableExtra("mSelected");
        requestReviews(mSelected.getId());

        title.setText(mSelected.getTitle());
        rating.setText(mSelected.getRating());
        Picasso.get().load("http://image.tmdb.org/t/p/original/" + mSelected.getPosterUrl()).into(poster);
        dateRelease.setText(mSelected.getDateRelease());
        desc.setText(mSelected.getOverview());

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

    private void requestReviews(final int id) {

        Retrofit retrofit = null;
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(MainActivity.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        final MovieService movieService = retrofit.create(MovieService.class);

        Call<ReviewRespond> rCall = movieService.getReviews(id+ "", MainActivity.API_KEY);
        rCall.enqueue(new Callback<ReviewRespond>() {
            @Override
            public void onResponse(Call<ReviewRespond> call, Response<ReviewRespond> response) {
                reviewsList.clear();
                reviewsList.addAll(response.body().getReviewsList());
                Log.d(TAG+"Review Id in" ,   " Size "+reviewsList.size());
                for (Review r : reviewsList) {
                    Log.d("Reviews  ",  r.getUrl());
                }
                reviewAdapter = new ReviewAdpater(reviewsList,getApplication());
                rRecycleView.setAdapter(reviewAdapter);
                reviewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ReviewRespond> call, Throwable t) {
                Log.e(TAG, "Error Failure on rCall enqueue No respond");
            }
        });
    }
}
