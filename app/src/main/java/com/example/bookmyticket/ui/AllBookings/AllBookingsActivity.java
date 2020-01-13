package com.example.bookmyticket.ui.AllBookings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.bookmyticket.R;
import com.example.bookmyticket.data.local.db.BookingEntity;

import java.util.List;

public class AllBookingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    AllBookingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bookings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler_booking);
        AllBookingsViewModel allBookingsViewModel = new ViewModelProvider(this).get(AllBookingsViewModel.class);

        allBookingsViewModel.getAllBookings();
        allBookingsViewModel.mList.observe(this, bookingEntities -> {

            if(bookingEntities != null)
            {
                Log.d("booking--", "init: LIST IN OBSERVE -- "+bookingEntities.size());
                setRecyclerView(bookingEntities);
            }
        });
    }

    private void setRecyclerView(List<BookingEntity> bookingEntities)
    {
        Log.d("booking--", "setRecyclerView: list size "+bookingEntities.size());
        adapter = new AllBookingsAdapter(bookingEntities, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }else return super.onOptionsItemSelected(item);
    }
}
