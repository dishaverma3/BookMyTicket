package com.example.bookmyticket.ui.MovieList;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookmyticket.BuildConfig;
import com.example.bookmyticket.data.remote.APIService;
import com.example.bookmyticket.data.remote.RetrofitClient;
import com.example.bookmyticket.model.Movie;
import com.example.bookmyticket.data.remote.model.Results;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MovieListViewModel extends AndroidViewModel {

    private List<Movie> movieArrayList;

    private MutableLiveData<Boolean> _onMovieFetched = new MutableLiveData<>();

    public MovieListViewModel(@NonNull Application application) {
        super(application);
    }

    void setMovieList()
    {
        Log.d("ttteeesssttt", "setMovieList: ");

        movieListRequest();
    }

    LiveData<Boolean> getOnMovieFetched() {
        return _onMovieFetched;
    }

    private void movieListRequest() {
        movieArrayList = new ArrayList<>();
        APIService apiService = RetrofitClient.getAPIService();

        Call<Results> call = apiService.getMovieList(BuildConfig.apiKey);

        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(@NonNull Call<Results> call, @NonNull retrofit2.Response<Results> response) {

                if (response.body() != null) {
                    movieArrayList.addAll(response.body().getList());
                }
                _onMovieFetched.setValue(true);
            }

            @Override
            public void onFailure(@NonNull Call<Results> call, @NonNull Throwable t) {

            }
        });
    }

    List<Movie> getMovies() {

        return movieArrayList;
    }

    boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
