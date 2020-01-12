package com.example.bookmyticket.ui.ConfirmBooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.bookmyticket.R;

public class ConfirmTicketActivity extends AppCompatActivity {

    private TextView locationSelected,selectedSeats,noOfPersons, movieName, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_ticket);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        movieName.setText(getIntent().getExtras().getString("movie_name"));
        time.setText(String.format("Time : %s", getIntent().getExtras().getString("time")));
        selectedSeats.setText(String.format("Selected Seats : %s", getIntent().getExtras().getString("Seats")));
        noOfPersons.setText(String.format("No. of Persons : %s", getIntent().getExtras().getString("no_of_persons")));
        locationSelected.setText(String.format("Location Selected : %s", getIntent().getExtras().getString("location")));
    }

    private void init() {
        movieName = findViewById(R.id.movie_name);
        selectedSeats = findViewById(R.id.selected_seats);
        noOfPersons = findViewById(R.id.total_persons);
        locationSelected = findViewById(R.id.location);
        time = findViewById(R.id.time);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
