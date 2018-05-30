package com.example.android.popularmovies.Model;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @GET("movie/{category}")
    Call<MovieRespond> getMovies(@Path("category") String category, @Query("api_key") String apiKey);
//    @GET("movie/popular")
//    Call<MovieRespond> getMoviePopular(@Query("api_key") String api_key);
//    @GET("movie/top_rated")
//    Call<MovieRespond> getMovieTopRate(@Query("api_key") String api_key);


}
