package com.example.bookmyticket.ui.AllBookings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookmyticket.data.local.db.BookingEntity;
import com.example.bookmyticket.data.local.db.Database;

import java.util.List;

public class AllBookingsViewModel extends AndroidViewModel {

    public LiveData<List<BookingEntity>> list = new MutableLiveData<>();
    private Boolean fetch = false;
    private Database appDatabase;

    public AllBookingsViewModel(@NonNull Application application) {
        super(application);

        appDatabase = Database.getInstance(application.getApplicationContext());
    }

    public LiveData<List<BookingEntity>> getAllBookings()
    {
        if(!fetch)
        {
            fetch = true;
            new Thread(() -> list = appDatabase.bookingDAO().fetchAllDetails()).start();
        }

        return list;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        appDatabase.close();
        list = null;
    }
}
