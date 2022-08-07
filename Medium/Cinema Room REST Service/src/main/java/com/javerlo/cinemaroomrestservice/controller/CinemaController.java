package com.javerlo.cinemaroomrestservice.controller;


import com.javerlo.cinemaroomrestservice.entity.*;
import com.javerlo.cinemaroomrestservice.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CinemaController {
    static final Cinema cinema = new Cinema(9, 9);
    static final List<Ticket> ticketList = new ArrayList<>();

    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> checkSeat(@RequestBody Seat seat){
        int row = seat.getRow();
        int column = seat.getColumn();
        if(row > 9 || column > 9 || row < 1 || column < 1){
            return new ResponseEntity<>(ResponseHandler.wrongSeat(), HttpStatus.BAD_REQUEST);
        }
        Seat seatToBook = cinema.getSeat(row, column);
        if(seatToBook != null && seatToBook.isTaken() ){
            return new ResponseEntity<>(ResponseHandler.takenSeat(), HttpStatus.BAD_REQUEST);
        }
        Ticket ticket = new Ticket(UUID.randomUUID(), seatToBook);
        ticketList.add(ticket);
        cinema.getSeat(row, column).setTaken(true);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Ticket returnTicket){
        for(Ticket ticket : ticketList){
            if(ticket.getId().compareTo(returnTicket.getId()) == 0){
                Seat seat = ticket.getSeat();
                int row = seat.getRow();
                int column = seat.getColumn();
                cinema.getSeat(row, column).setTaken(false);
                ticketList.remove(ticket);
                ReturnedTicket returnedTicket = new ReturnedTicket(ticket);
                return new ResponseEntity<>(returnedTicket, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(ResponseHandler.wrongToken(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getStatistics(@RequestParam Optional<String> password){
        if(password.isPresent()){
            return new ResponseEntity<>(new Statistic(cinema.getCurrentIncome(),
                    cinema.getNumberOfAvailableSeats(), ticketList.size()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseHandler.wrongPassword(), HttpStatus.UNAUTHORIZED);
        }
    }
}
