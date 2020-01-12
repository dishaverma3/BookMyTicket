package com.example.bookmyticket.ui.MovieList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookmyticket.BuildConfig;
import com.example.bookmyticket.R;
import com.example.bookmyticket.model.Movie;
import com.example.bookmyticket.ui.BookTicket.BookTicketActivity;
import com.example.bookmyticket.ui.MovieDetails.MovieDetailsActivity;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie> list;

    MovieAdapter(Context context, List<Movie> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_movie,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        final Movie listItem = list.get(position);

        holder.name.setText(listItem.getTitle());
        holder.releaseDate.setText(listItem.getReleaseDate());
        holder.adult.setText(listItem.getAdult().equals(true)? "Adult" : "Adult/Children");

        Glide.with(context).load(BuildConfig.baseImageUrl+ "w185/"+listItem.getPosterPath()).into(holder.poster);

        holder.cardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Intent i = new Intent(context, MovieDetailsActivity.class);
            bundle.putInt("movie_id",listItem.getId());
            i.putExtras(bundle);
            i.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });

        holder.bookButton.setOnClickListener(v -> {
            Intent i = new Intent(context.getApplicationContext(), BookTicketActivity.class);
            Bundle b = new Bundle();
            b.putString("movie_name",listItem.getTitle());
            i.addFlags(FLAG_ACTIVITY_NEW_TASK);
            i.putExtras(b);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, releaseDate, adult, bookButton;
        ImageView poster;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.movie_name);
            releaseDate = itemView.findViewById(R.id.release_date);
            adult = itemView.findViewById(R.id.movie_adult);
            poster = itemView.findViewById(R.id.poster_image);
            bookButton = itemView.findViewById(R.id.book_button);

            cardView = itemView.findViewById(R.id.card);

        }
    }
}
