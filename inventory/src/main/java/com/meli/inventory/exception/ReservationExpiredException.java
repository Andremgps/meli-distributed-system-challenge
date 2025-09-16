package com.meli.inventory.exception;

public class ReservationExpiredException extends RuntimeException{
    public ReservationExpiredException(String message) {
        super(message);
    }
}
