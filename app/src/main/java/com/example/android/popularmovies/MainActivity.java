package com.example.android.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies.Adapter.MoviesAdapter;
import com.example.android.popularmovies.Database.AppDatabase;
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

    private static final String TAG = "MainActivity.class";
    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    public final static String API_KEY = "Apikey";
    public final static String MOVIELIST_KEY = "MOVIELIST_KEY";
    private static Retrofit retrofit = null;

    // Populate item in RV
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MoviesAdapter moviesAdapter;
    private MoviesAdapter favMoviesAdapter;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIELIST_KEY)){
            movieList = savedInstanceState.getParcelableArrayList(MOVIELIST_KEY);
            Log.d(TAG,"Retrieve data from SaveInstanceStance");
        }

        // open connection to api default sorting on popular
        if (isOnline()) { sortedMovieApi(getString(R.string.query_popular)); }

        // populate movie data on RV
        mRecyclerView = findViewById(R.id.rv_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setHasFixedSize(true);

        mDb = AppDatabase.getsInstance(getApplicationContext());
        setupViewModel();
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
        if (query.equals(getString(R.string.query_favorite))) {
            showFavoriteView();
            return;
        }
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
//                mIdList.clear();
                movieList.clear();
                movieList.addAll(response.body().getMovieslist());
                for(Movie m: movieList) {
//                    mIdList.add(m.getId());
                    Log.d("Movie's title", m.getId()+ " | " + m.getTitle() );
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

    private void showFavoriteView() {
        Log.d("Suppose to Show ", " FAVORITE UI");
        setupViewModel();
        Toast.makeText(this, "Show favor list", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIELIST_KEY, (ArrayList<? extends Parcelable>) movieList);
        Log.v(TAG, "Saving the bundle");
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMoives().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.d(TAG,"Receiving database update from LiveData in ViewModel");
                favMoviesAdapter = new MoviesAdapter(movies,getApplicationContext());
                mRecyclerView.setAdapter(favMoviesAdapter);
            }
        });
    }

    /*
        isOnline() method will check the internet connection stage to prevent app get crashing reference "https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out"
     */
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}





