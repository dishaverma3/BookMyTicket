package com.example.bookmyticket.data.remote;

import com.example.bookmyticket.data.remote.model.Image;
import com.example.bookmyticket.model.Details;
import com.example.bookmyticket.data.remote.model.Results;
import com.example.bookmyticket.data.remote.model.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService  {

    @GET("upcoming")
    Call<Results> getMovieList(@Query("api_key") String api_key);

    @GET("{movieId}")
    Call<Details> getMovieDetails(@Path("movieId") int movieId,@Query("api_key") String api_key);

    @GET("{movieId}/videos")
    Call<Video> getVideoLink(@Path("movieId") int movieId, @Query("api_key") String api_key);

    @GET("{movieId}/images")
    Call<Image> getPosterImages(@Path("movieId") int movieId, @Query("api_key") String api_key);
}
