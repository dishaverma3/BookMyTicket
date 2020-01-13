package com.example.bookmyticket.ui.AllBookings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmyticket.R;
import com.example.bookmyticket.data.local.db.BookingEntity;
import com.example.bookmyticket.ui.ConfirmBooking.ConfirmTicketActivity;
import com.vipulasri.ticketview.TicketView;

import java.util.List;

public class AllBookingsAdapter extends RecyclerView.Adapter<AllBookingsAdapter.ViewHolder> {

    private List<BookingEntity> list;
    private Context context;

    public AllBookingsAdapter(List<BookingEntity> list, Context context) {
        Log.d("booking--", "AllBookingsAdapter: "+list.size());
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AllBookingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_booking,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllBookingsAdapter.ViewHolder holder, int position) {
        BookingEntity listItem = list.get(position);

        holder.movieName.setText(listItem.getMovieName());
        holder.selectedSeats.setText("Seats : "+listItem.getSeatName());
        holder.location.setText(listItem.getLocation());

        holder.layout.setOnClickListener(v -> {
            Intent i = new Intent(context.getApplicationContext(), ConfirmTicketActivity.class);
            Bundle b = new Bundle();
            b.putString("Seats",listItem.getSeatName());
            b.putString("no_of_persons",listItem.getNoOfPersons()+"");
            b.putString("location",listItem.getLocation());
            b.putString("time",listItem.getTime());
            b.putString("movie_name",listItem.getMovieName());
            i.putExtras(b);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView movieName, selectedSeats, location;
        ConstraintLayout layout;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            movieName = itemView.findViewById(R.id.movie_name);
            selectedSeats = itemView.findViewById(R.id.selected_seats);
            location = itemView.findViewById(R.id.location);
            layout = itemView.findViewById(R.id.root_view);
        }
    }

}
