package com.example.bookmyticket.ui.AllBookings;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmyticket.R;
import com.example.bookmyticket.data.local.db.BookingEntity;

import java.util.List;

public class AllBookingsAdapter extends RecyclerView.Adapter<AllBookingsAdapter.ViewHolder> {

    private List<BookingEntity> list;
    private Context context;

    public AllBookingsAdapter(List<BookingEntity> list, Context context) {
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
        holder.selectedSeats.setText(listItem.getSeatName());
        holder.location.setText(listItem.getLocation());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView movieName, selectedSeats, location;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            movieName = itemView.findViewById(R.id.movie_name);
            selectedSeats = itemView.findViewById(R.id.selected_seats);
            location = itemView.findViewById(R.id.location);
        }
    }

    void setList(List<BookingEntity> bookingList)
    {
        Log.d("booking", "setList: LIST - "+bookingList.get(0).getMovieName());
        list = bookingList;
        notifyDataSetChanged();
    }
}
