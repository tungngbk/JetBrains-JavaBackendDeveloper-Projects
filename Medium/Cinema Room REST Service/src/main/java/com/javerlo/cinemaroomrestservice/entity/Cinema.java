package com.javerlo.cinemaroomrestservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    @JsonProperty("total_rows")
    private int totalRows;
    @JsonProperty("total_columns")
    private int totalColumns;
    @JsonProperty("available_seats")
    List<Seat> availableSeats;

    public Cinema(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = new ArrayList<>();
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                Seat seat = new Seat(i, j);
                availableSeats.add(seat);
            }
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Seat getSeat(int row, int column){
        for(Seat seat : this.availableSeats){
            if(seat.getRow() == row && seat.getColumn() == column){
                return seat;
            }
        }
        return null;
    }

    @JsonIgnore
    public int getCurrentIncome(){
        int income = 0;
        for(Seat seat : availableSeats){
            if(seat.isTaken()){
                income += seat.getPrice();
            }
        }
        return income;
    }

    @JsonIgnore
    public int getNumberOfAvailableSeats(){
        int totalSeats = availableSeats.size();
        for(Seat seat : availableSeats){
            if(seat.isTaken()){
                totalSeats--;
            }
        }
        return totalSeats;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }
}