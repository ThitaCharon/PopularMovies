package com.example.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
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
import com.example.android.popularmovies.Model.Video;
import com.example.android.popularmovies.Model.VideoRespond;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity.class : ";
    private static final String BASE_URL = "https://api.themoviedb.org";
    private final static String API_KEY = "Apikey";
    private final static String MOVIELIST_KEY = "MOVIELIST_KEY";
    private static Retrofit retrofit = null;
    // Poppulate item in RV
    private List<Movie> movieList = new ArrayList<>();
    private List<Video> videosList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // open connection to api
        if (isOnline()) { sortedMovieApi(getString(R.string.query_popular)); }

        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIELIST_KEY)){
            movieList = savedInstanceState.getParcelableArrayList(MOVIELIST_KEY);
            Log.d(TAG,"Retrieve data from SaveInstanceStance");
        }

        // populate movie data on RV
        mRecyclerView = findViewById(R.id.rv_view);
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
                return true;
            case R.id.rating_sorted:
                sortedMovieApi(getString(R.string.query_top_rated));
                return true;
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
        final MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieRespond> mCall = movieService.getMovies(query, API_KEY);

        mCall.enqueue(new Callback<MovieRespond>() {
            @Override
            public void onResponse(Call<MovieRespond> call, Response<MovieRespond> response) {
                movieList.clear();
                movieList.addAll(response.body().getMovieslist());

                for(Movie m: movieList) {
                    Log.d("From Respond id", m.getId() + "");
                    Log.d("From Respond title", m.getTitle());
                    Log.d("From Respond Overview", m.getOverview());
                }
                retrieveVideos(157336, movieService);

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


    private void retrieveVideos (int id, MovieService service){

        Call<VideoRespond> vCall = service.getVideos(157336+"",API_KEY);
        vCall.enqueue(new Callback<VideoRespond>() {
            @Override
            public void onResponse(Call<VideoRespond> call, Response<VideoRespond> response) {
                // TODO try get list of Video
                Log.d(TAG, "pass in retrieceVedios");
                videosList.clear();
                videosList.addAll(response.body().getVideoslist());

                for(Video v: videosList) {
                    Log.d("Video's id", v.getId() + "");
                    Log.d("Video's Name", v.getName());
                    Log.d("Video's Size and Site ", v.getSize() + v.getSite());
                }
            }
            @Override
            public void onFailure(Call<VideoRespond> call, Throwable t) {
                Log.e(TAG, "Error Failure on vCall enqueue No respond");
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIELIST_KEY, (ArrayList<? extends Parcelable>) movieList);
        Log.v(TAG, "Saving the bundle");
    }


    /*
        isOnline() method will check the internet connection stage to prevent app get crashing
        reference "https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out"
     */
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}





