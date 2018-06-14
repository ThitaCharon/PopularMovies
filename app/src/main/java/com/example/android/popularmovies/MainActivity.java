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

import com.example.android.popularmovies.Adapter.MoviesAdapter;
import com.example.android.popularmovies.Adapter.ReviewAdpater;
import com.example.android.popularmovies.Model.Movie;
import com.example.android.popularmovies.Model.MovieRespond;
import com.example.android.popularmovies.Model.MovieService;
import com.example.android.popularmovies.Model.Review;
import com.example.android.popularmovies.Model.ReviewRespond;
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

    private static final String TAG = "MainActivity.class";
    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    public final static String API_KEY = "Apikey";
    public final static String MOVIELIST_KEY = "MOVIELIST_KEY";
    public final static String TRAILERLIST_KEY = "TRAILERLIST_KEY";
    public final static String REVIEWLIST_KEY = "REVIEWLIST_KEY";
    private static Retrofit retrofit = null;
    // Poppulate item in RV
    private List<Movie> movieList = new ArrayList<>();
    private List<Video> trailersList = new ArrayList<>();
    private List<Review> reviewsList = new ArrayList<>();
    private List<Integer> mIdList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MoviesAdapter moviesAdapter;
    private ReviewAdpater reviewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // open connection to api
        if (isOnline()) { sortedMovieApi(getString(R.string.query_popular)); }

        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIELIST_KEY)){
            movieList = savedInstanceState.getParcelableArrayList(MOVIELIST_KEY);
            trailersList = savedInstanceState.getParcelableArrayList(TRAILERLIST_KEY);
            reviewsList = savedInstanceState.getParcelableArrayList(REVIEWLIST_KEY);
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
            case R.id.favorite_sorted:
                sortedMovieApi(getString(R.string.query_favorite));
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
                mIdList.clear();
                movieList.clear();

                movieList.addAll(response.body().getMovieslist());

                for(Movie m: movieList) {
                    mIdList.add(m.getId());
                    Log.d("Movie's title", m.getId()+ " | " + m.getTitle() );
//                    retrieveVideos(m.getId(), movieService);
//                    retrieveReviews(m.getId(), movieService);
                }

                moviesAdapter = new MoviesAdapter(movieList,getApplicationContext());
                mRecyclerView.setAdapter(moviesAdapter);
                moviesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieRespond> call, Throwable t) {
                Log.e(TAG, "Error Failure on mCall.enqueue No respond");
            }
        });
    }// end sortedMovieApi()

/**
    private void retrieveVideos (final int id, MovieService service){

        Call<VideoRespond> vCall = service.getVideos(id + "", API_KEY);
        vCall.enqueue(new Callback<VideoRespond>() {
            @Override
            public void onResponse(Call<VideoRespond> call, Response<VideoRespond> response) {
                trailersList.clear();
                trailersList.addAll(response.body().getVideoslist());
                Log.d("Video Id" , id + " Size " + trailersList.size() );
                for (Video v : trailersList) {
                    Log.d("Videos ",  YOUTUBE_BASE_URL+v.getKey() + "&append_to_response=videos | " + v.getName() + v.getSite() + v.getSize());
                }
            }

            @Override
            public void onFailure(Call<VideoRespond> call, Throwable t) {
                Log.e(TAG, "Error Failure on vCall enqueue No respond");
            }
        });
    }

    private void retrieveReviews(MovieService movieService) {
        for (Integer id :mIdList) {
        Call<ReviewRespond> rCall = movieService.getReviews(id+ "", API_KEY);
        rCall.enqueue(new Callback<ReviewRespond>() {
            @Override
            public void onResponse(Call<ReviewRespond> call, Response<ReviewRespond> response) {
                reviewsList.clear();
                reviewsList.addAll(response.body().getReviewsList());
                Log.d("Review Id" ,   " Size "+reviewsList.size());
                for (Review r : reviewsList) {
                    Log.d("Reviews  ",  r.getUrl());
                }
                reviewAdapter = new ReviewAdpater(reviewsList,getApplication());
                //TODO try to pass reviewAdapter to DetailActivity and Display as RECYCLEVIEW
            }
            @Override
            public void onFailure(Call<ReviewRespond> call, Throwable t) {
                Log.e(TAG, "Error Failure on rCall enqueue No respond");
            }
        });
        }
    }
**/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIELIST_KEY, (ArrayList<? extends Parcelable>) movieList);
        outState.putParcelableArrayList(TRAILERLIST_KEY, (ArrayList<? extends Parcelable>) trailersList);
        outState.putParcelableArrayList(REVIEWLIST_KEY, (ArrayList<? extends Parcelable>) reviewsList);
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





