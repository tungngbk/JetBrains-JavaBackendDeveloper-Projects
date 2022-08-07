package com.javerlo.cinemaroomrestservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistic {
    private int currentIncome;
    private int availableSeats;
    private int purchasedTickets;

    @JsonProperty("current_income")
    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    @JsonProperty("number_of_available_seats")
    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @JsonProperty("number_of_purchased_tickets")
    public int getPurchasedTickets() {
        return purchasedTickets;
    }

    public void setPurchasedTickets(int purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }

    public Statistic() {
    }

    public Statistic(int currentIncome, int availableSeats, int purchasedTickets) {
        this.currentIncome = currentIncome;
        this.availableSeats = availableSeats;
        this.purchasedTickets = purchasedTickets;
    }
}
