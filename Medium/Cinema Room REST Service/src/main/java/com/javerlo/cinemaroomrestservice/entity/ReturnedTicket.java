package com.javerlo.cinemaroomrestservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ReturnedTicket {
    private UUID id;
    private Seat seat;

    @JsonIgnore
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty("returned_ticket")
    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public ReturnedTicket(Ticket ticket) {
        this.id = ticket.getId();
        this.seat = ticket.getSeat();
    }

    public ReturnedTicket() {
    }
}
