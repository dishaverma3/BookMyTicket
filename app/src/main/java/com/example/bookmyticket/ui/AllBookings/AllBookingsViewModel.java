package com.example.bookmyticket.ui.AllBookings;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookmyticket.data.local.db.BookingEntity;
import com.example.bookmyticket.data.local.db.Database;

import java.util.ArrayList;
import java.util.List;

public class AllBookingsViewModel extends AndroidViewModel {
    List<BookingEntity> list = new ArrayList<>();
    public MutableLiveData<List<BookingEntity>> mList = new MutableLiveData<>();
    private Database appDatabase;

    public AllBookingsViewModel(@NonNull Application application) {
        super(application);

        appDatabase = Database.getInstance(application.getApplicationContext());
    }

    void getAllBookings() {


        Log.d("booking--", "getAllBookings: called");

        new Thread(new Runnable() {
            @Override
            public void run() {
                list = appDatabase.bookingDAO().fetchAllDetails();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mList.setValue(list);
                    }
                });

            }
        }).start();

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mList = null;
    }
}
