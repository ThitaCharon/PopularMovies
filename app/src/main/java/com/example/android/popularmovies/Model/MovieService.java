package com.example.android.popularmovies.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @GET("/3/movie/{category}")
    Call<MovieRespond> getMovies(@Path("category") String category, @Query("api_key") String apiKey);
//    @GET("/3/movie/{id}/videos")
//    Call<VideoRespond> getMovies(@Path("id") String id, @Query("api_key") String apiKey);
//    https://api.themoviedb.org/3/movie/157336/videos?api_key={api_key}

//    @GET("/3/movie/popular")
//    Call<MovieRespond> getMoviePopular(@Query("api_key") String api_key);
//    @GET("/3/movie/top_rated")
//    Call<MovieRespond> getMovieTopRate(@Query("api_key") String api_key);


}
