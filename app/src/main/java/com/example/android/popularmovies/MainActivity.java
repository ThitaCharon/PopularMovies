package com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.popularmovies.Model.Movie;
import com.example.android.popularmovies.Model.MovieRespond;
import com.example.android.popularmovies.Model.MovieService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity.class : ";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private final static String API_KEY = "ApiKey";
    public static final String IMAGE_URL_PATH = "http://image.tmdb.org/t/p/w185/";
    private static Retrofit retrofit = null;
    // Poppulate item in RV
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private List<Movie> mListItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // open connection to api
        openConnectionMovieApi();

        // populate movie data on RV
        recyclerView = (RecyclerView) findViewById(R.id.rv_posters);
         RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setHasFixedSize(true);
        moviesAdapter = new MoviesAdapter(mListItems);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
    }


    void openConnectionMovieApi() {
        // check if retrofit is null then create a new one
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieRespond> call = movieService.getMoviePopular(API_KEY);
        call.enqueue(new Callback<MovieRespond>() {
            @Override
            public void onResponse(Call<MovieRespond> call, Response<MovieRespond> response) {
                Log.d(TAG,"success");
                List<Movie> movieList = response.body().getMovieslist();
                for(Movie m : movieList){
                    Log.d("Title", m.getTitle());
                    Log.d("id",m.getId()+"");
                    Log.d("Poster", IMAGE_URL_PATH + m.getPoster());
                    Log.d("rating",m.getRating());
                    Log.d("popularity", m.getPopularity()+ "");
                    Log.d("over view ", m.getOverview());
                    Log.d("Date Relase" ,m.getDateRelease());
                    mListItems.add(new Movie(m.getTitle(),m.getId(),m.getPoster(),m.getRating(),m.getDateRelease(),m.getOverview()));
                }
                Log.d("Movie List size" , mListItems.size() + "");
                Log.d(TAG, "Total # of movies : " + movieList.size());
            }

            @Override
            public void onFailure(Call<MovieRespond> call, Throwable t) {
                Log.e(TAG, "Error Failure on call.enqueue No responmi.d");
            }
        });

    }// end openConnectionMovieApi()

}





