package com.example.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    private static final String BASE_URL = "https://api.themoviedb.org";
    private final static String API_KEY = "ApiKey";
    public static final String IMAGE_URL_PATH = "http://image.tmdb.org/t/p/w342/";

    private static Retrofit retrofit = null;
    // Poppulate item in RV
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // open connection to api
        if (isOnline()) {
            sortedMovieApi(getString(R.string.query_popular));
        }
        // populate movie data on RV
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setHasFixedSize(true);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.popularity_sorted:
                sortedMovieApi(getString(R.string.query_popular));
            case R.id.rating_sorted:
                sortedMovieApi(getString(R.string.query_top_rated));
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void sortedMovieApi(String query){
        // check if retrofit is null then create a new one
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieRespond> call = movieService.getMovies(query, API_KEY);
        Log.d("Query ", query);
        call.enqueue(new Callback<MovieRespond>() {
            @Override
            public void onResponse(Call<MovieRespond> call, Response<MovieRespond> response) {
                Log.d(TAG,"success");
                movieList.clear();
                movieList.addAll(response.body().getMovieslist());
                for(Movie m : movieList){
                    Log.d("Title", m.getTitle());
                    Log.d("id",m.getId()+"");
                    Log.d("Poster", IMAGE_URL_PATH + m.getPosterUrl());
                    Log.d("rating",m.getRating());
                    Log.d("popularity", m.getPopularity()+ "");
                    Log.d("over view ", m.getOverview());
                    Log.d("Date Release" ,m.getDateRelease());
            }
                Log.d(TAG, "Total # of movies : " + movieList.size());
                moviesAdapter = new MoviesAdapter(movieList,getApplicationContext());
                mRecyclerView.setAdapter(moviesAdapter);
                moviesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieRespond> call, Throwable t) {
                Log.e(TAG, "Error Failure on call.enqueue No respond");
            }
        });
    }// end sortedMovieApi()


    /*
        isOnline() method will check the internet connection stage to prevent app get crashing
        reference "https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out"
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}





