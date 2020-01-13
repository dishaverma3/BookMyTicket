package com.example.bookmyticket.data.local.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookingDetails(BookingEntity booking);

    @Query("SELECT * FROM booking_table")
    List<BookingEntity> fetchAllDetails();

    @Delete
    void deleteDetails(BookingEntity bookingEntity);

}
