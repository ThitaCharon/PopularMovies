package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Adapter.ReviewAdpater;
import com.example.android.popularmovies.Adapter.VideoAdpater;
//import com.example.android.popularmovies.Database.AppDatabase;
import com.example.android.popularmovies.Model.Movie;
import com.example.android.popularmovies.Model.MovieService;
import com.example.android.popularmovies.Model.Review;
import com.example.android.popularmovies.Model.ReviewRespond;
import com.example.android.popularmovies.Model.Video;
import com.example.android.popularmovies.Model.VideoRespond;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    TextView title;
    TextView rating ;
    TextView dateRelease ;
    TextView desc ;
    ImageView poster;
    private List<Video> trailersList = new ArrayList<>();
    private VideoAdpater videoAdpater;
    private RecyclerView vRecycleView;
    private List<Review> reviewsList = new ArrayList<>();
    private ReviewAdpater reviewAdapter;
    private RecyclerView rRecycleView;
    private Button favBtn;
    private final String TAG = DetailActivity.class.getSimpleName();
    private Movie favMovie , mSelected;
    // variable for Database
//    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        initViews();

        //Testing database
//        mDb = AppDatabase.getsInstance(getApplicationContext());

        // setup RecycleView for Trailer
        initRecycleView();

        // extract data
        Intent intent = getIntent();
        mSelected = intent.getParcelableExtra("mSelected");

        //Retrofit callback api
        requestTrailer(mSelected.getId());

        // set data to display
        displayData();

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favBtn.setText(R.string.my_favorite);
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked(){
        Log.d("Insert Movie", " to a Favorite DB");
//        mDb.movieDAO().insertMovie(favMovie);
//        finish();
    }


    private void requestTrailer(final int id){
        Retrofit retrofit = null;
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(MainActivity.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        final MovieService movieService = retrofit.create(MovieService.class);
        Call<VideoRespond> vCall = movieService.getVideos(id + "", MainActivity.API_KEY);
        vCall.enqueue(new Callback<VideoRespond>() {
            @Override
            public void onResponse(Call<VideoRespond> call, Response<VideoRespond> response) {
                trailersList.clear();
                trailersList.addAll(response.body().getVideoslist());
                Log.d("Video Id" , id + " Size " + trailersList.size() );
                for (Video v : trailersList) {
                    Log.d("Videos ",  MainActivity.YOUTUBE_BASE_URL+v.getKey() + "&append_to_response=videos | " + v.getName() + v.getSite() + v.getSize());
                }
                videoAdpater = new VideoAdpater(trailersList,getApplication());
                vRecycleView.setAdapter(videoAdpater);
                videoAdpater.notifyDataSetChanged();
                requestReviews(id, movieService);
            }

            @Override
            public void onFailure(Call<VideoRespond> call, Throwable t) {
                Log.e(TAG, "Error Failure on vCall enqueue No respond");
            }
        });
    }
    private void requestReviews(final int id, MovieService movieService ) {
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

    private void initViews() {
        title = findViewById(R.id.tv_detailActivity_Title);
        rating = findViewById(R.id.tv_detailActivity_Rating);
        dateRelease = findViewById(R.id.tv_detailActivity_DateRelease);
        desc =  findViewById(R.id.tv_detailActivity_desc);
        poster = findViewById(R.id.tv_detailActivity_posterimage);
        favBtn = findViewById(R.id.btn_favorite);
    }
    private void displayData() {
        title.setText(mSelected.getTitle());
        rating.setText(mSelected.getRating());
        dateRelease.setText(mSelected.getDateRelease());
        desc.setText(mSelected.getOverview());
        Picasso.get().load("http://image.tmdb.org/t/p/original/" + mSelected.getPosterUrl()).into(poster);

    }
    private void initRecycleView() {
        vRecycleView = findViewById(R.id.rv_trailer);
        vRecycleView.setLayoutManager(new LinearLayoutManager(this));
        vRecycleView.setHasFixedSize(false);
        // setup RecycleView for Review
        rRecycleView = findViewById(R.id.rv_review);
        rRecycleView.setLayoutManager(new LinearLayoutManager(this));
        rRecycleView.setHasFixedSize(true);
    }

}
