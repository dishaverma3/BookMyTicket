package com.example.bookmyticket.ui.MovieDetails;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookmyticket.BuildConfig;
import com.example.bookmyticket.data.remote.model.Image;
import com.example.bookmyticket.data.remote.APIService;
import com.example.bookmyticket.data.remote.RetrofitClient;
import com.example.bookmyticket.model.Details;
import com.example.bookmyticket.data.remote.model.Video;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsViewModel extends AndroidViewModel {

    private APIService apiService;
    MutableLiveData<String[]> posterImages = new MutableLiveData<>();
    MutableLiveData<String> videoKey = new MutableLiveData<>();
    MutableLiveData<Details> movieDetails = new MutableLiveData<>();

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);

        apiService = RetrofitClient.getAPIService();
    }

    void movieDetailsRequest(int id)
    {

        Call<Details> call = apiService.getMovieDetails(id,BuildConfig.apiKey);

        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(@NonNull Call<Details> call, @NonNull retrofit2.Response<Details> response) {
                movieDetails.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Details> call, @NonNull Throwable t) {

            }
        });
    }

    void movieVideoLink(int id)
    {
        Call<Video> call = apiService.getVideoLink(id, BuildConfig.apiKey);

        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(@NonNull Call<Video> call, @NonNull Response<Video> response) {
                if (response.body() != null) {
                    videoKey.setValue(response.body().getResults().get(0).getKey());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Video> call, @NonNull Throwable t) {

            }
        });
    }


    void moviePosterImage(int id)
    {
        Call<Image> call = apiService.getPosterImages(id,BuildConfig.apiKey);

        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(@NonNull Call<Image> call, @NonNull Response<Image> response) {
                String [] images = new String[5];
                int length = 5;

                if (response.body() != null && response.body().getBackdrops().size() < 5)
                    length = response.body().getBackdrops().size();

                for(int i = 0; i < length; i++)
                {
                    if (response.body() != null) {
                        images[i] = response.body().getBackdrops().get(i).getFilePath();
                    }
                }
                posterImages.setValue(images);
            }

            @Override
            public void onFailure(@NonNull Call<Image> call, @NonNull Throwable t) {

            }
        });
    }

    boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
