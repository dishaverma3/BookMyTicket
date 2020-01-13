package com.example.bookmyticket.ui.BookTicket;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookmyticket.data.local.db.BookingEntity;
import com.example.bookmyticket.data.local.db.Database;
import com.example.bookmyticket.model.SeatExample;

import java.util.HashSet;

import by.anatoldeveloper.hallscheme.hall.HallScheme;
import by.anatoldeveloper.hallscheme.hall.ScenePosition;
import by.anatoldeveloper.hallscheme.hall.Seat;
import by.anatoldeveloper.hallscheme.hall.SeatListener;
import by.anatoldeveloper.hallscheme.view.ZoomableImageView;

public class BookTicketViewModel extends AndroidViewModel {

    private Application application;
    SeatExample[][] seats;
    private Database appDatabase;
    private HashSet<String> selectedSeats = new HashSet<>();
    public String timeFinal;
    public MutableLiveData<HashSet<String>> selectionSet = new MutableLiveData<>();

    public MutableLiveData<Boolean> time1 = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> time2 = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> time3 = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> time4 = new MutableLiveData<>(false);

    public BookTicketViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        appDatabase = Database.getInstance(application.getApplicationContext());
    }

    public Seat[][] schemeWithScene() {
        seats = new SeatExample[16][29];
        int k = 0;
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 29; j++) {
                SeatExample seat = new SeatExample();
                seat.id = ++k;
                seat.selectedSeatMarker = String.valueOf(j + 1);
                seat.status = HallScheme.SeatStatus.EMPTY;
                seat.setSeatName((char) (i + 'A') + String.valueOf(j+1));
                seats[i][j] = seat;
                if (i < 5 && j < 4) {
                    seat.status = HallScheme.SeatStatus.BUSY;
                    if (i < 4) {
                        seat.status = HallScheme.SeatStatus.FREE;
                        seat.color = Color.argb(255, 60, 179, 113);
                    }
                }
                if (j > 1 && j < 5 && i > 4) {
                    seat.status = HallScheme.SeatStatus.BUSY;
                    if (i > 13) {
                        seat.status = HallScheme.SeatStatus.FREE;
                        seat.color = Color.argb(255, 148, 51, 145);
                    }
                }
                if (j == 4 && i > 1 && i < 5)
                    seat.status = HallScheme.SeatStatus.BUSY;
                if (i < 5 && j > 24) {
                    seat.status = HallScheme.SeatStatus.BUSY;
                    if (i < 4) {
                        seat.status = HallScheme.SeatStatus.FREE;
                        seat.color = Color.argb(255, 60, 179, 113);
                    }
                }
                if (j > 23 && j < 27 && i > 4) {
                    seat.status = HallScheme.SeatStatus.BUSY;
                    if (i > 13) {
                        seat.status = HallScheme.SeatStatus.FREE;
                        seat.color = Color.argb(255, 148, 51, 145);
                    }
                }
                if (j == 24 && i > 1 && i < 5)
                    seat.status = HallScheme.SeatStatus.BUSY;
                if (i > 3 && j > 6 && j < 22) {
                    seat.status = HallScheme.SeatStatus.BUSY;
                    if (i > 13) {
                        seat.status = HallScheme.SeatStatus.FREE;
                        seat.color = Color.argb(255, 43, 108, 196);
                    }
                }

            }
        return seats;
    }

    public void setScheme(ZoomableImageView zoomableImageView) {
        final HallScheme scheme = new HallScheme(zoomableImageView, schemeWithScene(), application.getApplicationContext());
        scheme.setScenePosition(ScenePosition.SOUTH);

        if(!selectedSeats.isEmpty()){
            for(String value : selectedSeats)
            {
                getSeatId(value);
            }
        }

        scheme.setSeatListener(new SeatListener() {
            @Override
            public void selectSeat(int id) {
                selectedSeats.add(getSeatName(id));
            }

            @Override
            public void unSelectSeat(int id) {
                selectedSeats.remove(getSeatName(id));
            }

        });
    }

    public int getSeatId(String name) {
        int id = 0;
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 29; j++) {
                if(seats[i][j].getSeatName() == name)
                {
                    id = seats[i][j].id();
                }
            }
        return id;
    }

    private String getSeatName(int id) {
        String name = "";
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 29; j++) {
                if(seats[i][j].id() == id)
                {
                    name = seats[i][j].getSeatName();
                }
            }
        return name;
    }

    public void selectSeat()
    {
        selectionSet.setValue(selectedSeats);
    }

    public void insertBookingDetails(BookingEntity bookingDetails) {
        Log.d("booking--", "insertBookingDetails: before");
        appDatabase.bookingDAO().insertBookingDetails(bookingDetails);
        Log.d("booking--", "insertBookingDetails: after");
        Log.d("booking--", "openConfirmTicket: size "+appDatabase.bookingDAO().fetchAllDetails().size());

    }

    public void timeSelection(int id) {
        switch (id)
        {
            case 1 : time1.setValue(!time1.getValue()); time2.setValue(false); time3.setValue(false); time4.setValue(false); break;
            case 2 : time2.setValue(!time2.getValue()); time1.setValue(false); time3.setValue(false); time4.setValue(false); break;
            case 3 : time3.setValue(!time3.getValue()); time2.setValue(false); time1.setValue(false); time4.setValue(false); break;
            case 4 : time4.setValue(!time4.getValue()); time2.setValue(false); time3.setValue(false); time1.setValue(false); break;
        }

    }
}
