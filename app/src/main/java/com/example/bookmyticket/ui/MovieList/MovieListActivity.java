package com.example.bookmyticket.ui.MovieList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmyticket.R;
import com.example.bookmyticket.model.Movie;
import com.example.bookmyticket.ui.AllBookings.AllBookingsActivity;

import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private static final String TAG = "MovieListActivity";

    private MovieListViewModel movieListViewModel;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        recyclerView = findViewById(R.id.recycler);

        movieListViewModel.setMovieList();
        movieListViewModel.getOnMovieFetched().observe(this, aBoolean -> {
            setRecyclerView(movieListViewModel.getMovies());
            Log.d(TAG, "onChanged: "+movieListViewModel.getMovies().size());
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.all_bookings)
        {
            startActivity(new Intent(this, AllBookingsActivity.class));
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    public void setRecyclerView(List<Movie> moviesArrayList) {
        MovieAdapter adapter = new MovieAdapter(getApplicationContext(), moviesArrayList);
        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
