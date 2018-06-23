package com.example.android.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.Database.AppDatabase;
import com.example.android.popularmovies.Model.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> moives;
    private static final String TAG = MainViewModel.class.getSimpleName();

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving movies from the Database");
        moives = database.movieDAO().loadAllMovies();
    }

    public LiveData<List<Movie>> getMoives(){   return moives; }
}
