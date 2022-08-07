package com.javerlo.cinemaroomrestservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Ticket {
    private UUID id;
    private Seat seat;

    @JsonProperty("token")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty("ticket")
    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Ticket(UUID id, Seat seat) {
        this.id = id;
        this.seat = seat;
    }

    public Ticket() {
    }
}
