package com.example.bookmyticket.ui.BookTicket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.bookmyticket.R;
import com.example.bookmyticket.data.local.db.BookingEntity;
import com.example.bookmyticket.ui.ConfirmBooking.ConfirmTicketActivity;

import java.util.HashSet;


public class BookTicketActivity extends AppCompatActivity implements View.OnClickListener {

    Button showSeats;
    private BookTicketViewModel bookTicketViewModel;
    TextView selectedSeatsView,movieName;
    HashSet<String> selectedSeats = new HashSet<>();
    private TextView noOfPersons;
    private String locationSelected = "";
    private RadioGroup radioGroup;
    private RadioButton radioLocation1,radioLocation2,radioLocation3,radioLocation4;
    private TextView bookTicketButton, time1 ,time2 ,time3 , time4;
    private String seatsValue,noOfPersonsValue;
    private LinearLayout timeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        showSeats.setOnClickListener(v -> {
            showSeats();
            selectedSeatsView.setTextColor(Color.BLACK);
        });

        bookTicketViewModel.selectionSet.observe(this, strings -> {
            selectedSeats = strings;
            if(!strings.isEmpty())
            {
                StringBuilder seats = new StringBuilder();
                Log.d("kjskj", "onChanged: SELECTED SEATS ARE -" + strings);
                for(String value : strings)
                {
                    seats.append(value).append(",");
                }
                seats.deleteCharAt(seats.length()-1);
                seatsValue = seats.toString();
                noOfPersonsValue = String.valueOf(strings.size());
                selectedSeatsView.setText(String.format("%s %s", getString(R.string.selected_seats),seats));
                noOfPersons.setText(String.format("%s %s", getString(R.string.no_of_persons),strings.size()));

            }
        });

        time1.setOnClickListener(this);
        time2.setOnClickListener(this);
        time3.setOnClickListener(this);
        time4.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId)
            {
                case R.id.location1 : locationSelected = radioLocation1.getText().toString(); break;
                case R.id.location2 : locationSelected = radioLocation2.getText().toString(); break;
                case R.id.location3 : locationSelected = radioLocation3.getText().toString(); break;
                case R.id.location4 : locationSelected = radioLocation4.getText().toString(); break;
            }

            radioLocation1.setTextColor(Color.BLACK);
            radioLocation2.setTextColor(Color.BLACK);
            radioLocation3.setTextColor(Color.BLACK);
            radioLocation4.setTextColor(Color.BLACK);
        });

        bookTicketButton.setOnClickListener(v -> openConfirmTicket());

        bookTicketViewModel.time1.observe(this, aBoolean -> changeTheme(aBoolean,time1));

        bookTicketViewModel.time2.observe(this, aBoolean -> changeTheme(aBoolean,time2));

        bookTicketViewModel.time3.observe(this, aBoolean -> changeTheme(aBoolean,time3));

        bookTicketViewModel.time4.observe(this, aBoolean -> changeTheme(aBoolean,time4));
    }

    private void changeTheme(Boolean aBoolean, TextView view) {
        if(aBoolean)
        {
            view.setBackground(getResources().getDrawable(R.color.colorAccent));
            view.setTextColor(Color.WHITE);
            bookTicketViewModel.timeFinal = view.getText().toString();
        }else {
            view.setBackground(getResources().getDrawable(R.drawable.border));
            view.setTextColor(Color.BLACK);
        }
    }

    private void openConfirmTicket() {
        if (TextUtils.isEmpty(seatsValue) )
        {
            selectedSeatsView.setError("Required");
            selectedSeatsView.setTextColor(Color.RED);
        } else if(TextUtils.isEmpty(locationSelected))
        {
            radioLocation1.setError("Required");
            radioLocation1.setTextColor(Color.RED);
            radioLocation2.setTextColor(Color.RED);
            radioLocation3.setTextColor(Color.RED);
            radioLocation4.setTextColor(Color.RED);
        }else {

            final String time = bookTicketViewModel.timeFinal;
            new Thread(() -> bookTicketViewModel.insertBookingDetails(new BookingEntity(getIntent().getExtras().getString("movie_name"),time,Integer.valueOf(noOfPersonsValue),locationSelected,seatsValue)));
            Intent i = new Intent(getApplicationContext(), ConfirmTicketActivity.class);
            Bundle b = new Bundle();
            b.putString("Seats",seatsValue);
            b.putString("no_of_persons",noOfPersonsValue);
            b.putString("location",locationSelected);
            b.putString("time",bookTicketViewModel.timeFinal);
            b.putString("movie_name",movieName.getText().toString());
            i.putExtras(b);
            startActivity(i);
            finish();
        }
    }

    private void init() {
        movieName = findViewById(R.id.movie_name);
        showSeats = findViewById(R.id.scheme_button);
        selectedSeatsView = findViewById(R.id.selected_seats);
        noOfPersons = findViewById(R.id.total_persons);
        radioGroup = findViewById(R.id.location_radio_group);
        radioLocation1 = findViewById(R.id.location1);
        radioLocation2 = findViewById(R.id.location2);
        radioLocation3 = findViewById(R.id.location3);
        radioLocation4 = findViewById(R.id.location4);
        timeLayout = findViewById(R.id.time_layout);

        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        time3 = findViewById(R.id.time3);
        time4 = findViewById(R.id.time4);

        bookTicketButton = findViewById(R.id.book_button);

        movieName.setText("Movie - "+getIntent().getExtras().getString("movie_name"));
        selectedSeatsView.setText(String.format("%s None", getString(R.string.selected_seats)));
        noOfPersons.setText(String.format("%s 0",getString(R.string.no_of_persons)));

        bookTicketViewModel = new ViewModelProvider(this).get(BookTicketViewModel.class);

    }

    public void showSeats() {
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this).create();

        // get dialog.xml view
        View promptsView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_seats, null);
        Button done = promptsView.findViewById(R.id.done_buttton);
        by.anatoldeveloper.hallscheme.view.ZoomableImageView zoomableImageView = promptsView.findViewById(R.id.zoomable_image);

        alertDialogBuilder.setView(promptsView);
        bookTicketViewModel.setScheme(zoomableImageView);

        done.setOnClickListener(v -> {
            Log.d("bookticketactivity", "onClick: ON CLICK BUTTON");
            bookTicketViewModel.selectSeat();
            alertDialogBuilder.dismiss();
        });

        alertDialogBuilder.show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.time1 : bookTicketViewModel.timeSelection(1); break;
            case R.id.time2 : bookTicketViewModel.timeSelection(2); break;
            case R.id.time3 : bookTicketViewModel.timeSelection(3); break;
            case R.id.time4 : bookTicketViewModel.timeSelection(4); break;
        }
    }
}