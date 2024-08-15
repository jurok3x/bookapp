package com.ykotsiuba.bookapp.exception;

public class BookCannotBeDeletedException extends RuntimeException {
    public BookCannotBeDeletedException(String message) {
        super(message);
    }
}
