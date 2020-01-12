package com.example.bookmyticket.data.local.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {BookingEntity.class}, version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract BookingDAO bookingDAO();

    private static Object lock = new Object();
    private static Database INSTANCE = null;

    public static Database getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (lock)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),Database.class,"AppDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
