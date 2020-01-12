package com.example.bookmyticket.ui.AllBookings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.bookmyticket.R;
import com.example.bookmyticket.data.local.db.BookingEntity;

import java.util.List;

public class AllBookingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bookings);

        init();
    }

    private void init()
    {
        recyclerView = findViewById(R.id.recycler_booking);
        AllBookingsViewModel allBookingsViewModel = new ViewModelProvider(this).get(AllBookingsViewModel.class);

        allBookingsViewModel.getAllBookings();

        allBookingsViewModel.list.observe(this, this::setRecyclerView);
    }

    private void setRecyclerView(List<BookingEntity> bookingEntities)
    {
        AllBookingsAdapter adapter = new AllBookingsAdapter(bookingEntities, this);
        recyclerView.setAdapter(adapter);
    }
}
