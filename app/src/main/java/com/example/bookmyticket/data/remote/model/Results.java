package com.example.bookmyticket.data.remote.model;

import com.example.bookmyticket.model.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {

    @SerializedName("results")
    @Expose
    private List<Movie> List;

    public Results(List<Movie> List) {
        this.List = List;
    }

    public List<Movie> getList() {
        return List;
    }

    public void setList(List<Movie> List) {
        this.List = List;
    }
}
