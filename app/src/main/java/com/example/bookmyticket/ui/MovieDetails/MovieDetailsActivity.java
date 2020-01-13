package com.example.bookmyticket.ui.MovieDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bookmyticket.R;
import com.example.bookmyticket.model.Details;
import com.example.bookmyticket.model.Genre;
import com.example.bookmyticket.ui.BookTicket.BookTicketActivity;
import com.example.bookmyticket.ui.Player.PlayerActivity;
import com.google.android.material.snackbar.Snackbar;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView name, releaseDate, genre, overview,bookButton;
    RatingBar ratingBar;
    String videoKey;
    Button trailer;
    int id;
    private MovieDetailsViewModel movieDetailsViewModel;
    private ViewPager viewPager;
    private ProgressBar progressBar;
    private DotsIndicator dotsIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

        movieDetailsViewModel.movieDetails.observe(this, this::setData);
        movieDetailsViewModel.videoKey.observe(this, this::setTrailer);
        movieDetailsViewModel.posterImages.observe(this, this::setViewPager);

        trailer.setOnClickListener(v -> {
            Intent i = new Intent(this,PlayerActivity.class);
            Bundle b = new Bundle();
            b.putString("videoKey",videoKey);
            i.putExtras(b);
            startActivity(i);
        });

        bookButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), BookTicketActivity.class);
            Bundle b = new Bundle();
            b.putString("movie_name",name.getText().toString());
            i.putExtras(b);
            startActivity(i);
        });
    }

    private void setViewPager(String[] strings) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, strings);
        viewPager.setAdapter(viewPagerAdapter);
        dotsIndicator.setViewPager(viewPager);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setTrailer(String key) {
        trailer.setEnabled(true);
        videoKey = key;
    }

    private void init() {
        name = findViewById(R.id.movie_name);
        releaseDate = findViewById(R.id.release_date_details);
        genre = findViewById(R.id.genres);
        overview = findViewById(R.id.movie_overview);
        ratingBar = findViewById(R.id.rating);
        trailer = findViewById(R.id.trailer_button);
        viewPager = findViewById(R.id.viewpager);
        progressBar = findViewById(R.id.progressBar_details);
        dotsIndicator = findViewById(R.id.dots_indicator);
        bookButton = findViewById(R.id.book_button);

        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        id = getIntent().getExtras().getInt("movie_id");

        trailer.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);


        if(movieDetailsViewModel.isNetworkConnected())
        {
            movieDetailsViewModel.movieDetailsRequest(id);
            movieDetailsViewModel.movieVideoLink(id);
            movieDetailsViewModel.moviePosterImage(id);
        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"No Internet Connection !!",Snackbar.LENGTH_LONG);
            (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            snackbar.show();

            progressBar.setVisibility(View.GONE);
        }

    }

    private void setData(Details details) {
        name.setText(details.getTitle());
        releaseDate.setText(String.format("Release Date : %s", details.getReleaseDate()));
        genre.setText(getAllGenres(details));
        ratingBar.setRating(calculateRating(details.getVoteAverage()));
        overview.setText(details.getOverview());
    }

    private float calculateRating(Double voteAverage) {
        float percentage = (float) (voteAverage / 10) * 100;
        return ( 5 * percentage) / 100;
    }

    private String getAllGenres(Details details) {

        StringBuilder genres = new StringBuilder();
        for(Genre obj : details.getGenres())
        {
            genres.append(obj.getName());
            if(details.getGenres().size() > 1)
            {
                genres.append("/");
            }
        }

        genres.deleteCharAt(genres.length()-1);

        return genres.toString();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
