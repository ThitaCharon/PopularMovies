package com.example.android.popularmovies.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("/3/movie/{category}")
    Call<MovieRespond> getMovies(@Path("category") String category, @Query("api_key") String apiKey);

    @GET("/3/movie/{id}/videos?")
    Call<VideoRespond> getVideos(@Path("id") String id , @Query("api_key") String apiKey);

    @GET("/3/movie/{id}/reviews?")
    Call<ReviewRespond> getReviews(@Path("id") String id, @Query("api_key") String apiKey);



}
