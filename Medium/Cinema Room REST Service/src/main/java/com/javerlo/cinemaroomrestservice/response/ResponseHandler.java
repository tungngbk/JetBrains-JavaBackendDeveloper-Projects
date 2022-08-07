package com.javerlo.cinemaroomrestservice.response;


public class ResponseHandler {
    public static String wrongSeat() {
        return "{ \"error\": \"The number of a row or a column is out of bounds!\" }";
    }

    public static String takenSeat() {
        return "{ \"error\": \"The ticket has been already purchased!\" }";
    }

    public static String wrongToken() {
        return "{ \"error\": \"Wrong token!\" }";
    }

    public static String wrongPassword() {
        return "{ \"error\": \"The password is wrong!\" }";
    }
}
