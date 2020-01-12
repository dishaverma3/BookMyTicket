package com.example.bookmyticket.data.local.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "booking_table")
public class BookingEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "booking_id")
    private Long id;

    @NonNull
    private String movieName;

    @NonNull
    private String time;

    @NonNull
    private Integer noOfPersons;

    @NonNull
    private String location;

    @NonNull
    private String seatName;

    @Ignore
    public BookingEntity(Long id, @NonNull String movieName, @NonNull Integer noOfPersons, @NonNull String location, @NonNull String seatName) {
        this.id = id;
        this.movieName = movieName;
        this.noOfPersons = noOfPersons;
        this.location = location;
        this.seatName = seatName;
    }

    public BookingEntity(@NonNull String movieName, @NonNull String time,@NonNull Integer noOfPersons, @NonNull String location, @NonNull String seatName) {
        this.movieName = movieName;
        this.time = time;
        this.noOfPersons = noOfPersons;
        this.location = location;
        this.seatName = seatName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(@NonNull String movieName) {
        this.movieName = movieName;
    }

    @NonNull
    public Integer getNoOfPersons() {
        return noOfPersons;
    }

    public void setNoOfPersons(@NonNull Integer noOfPersons) {
        this.noOfPersons = noOfPersons;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    @NonNull
    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(@NonNull String seatName) {
        this.seatName = seatName;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }
}
