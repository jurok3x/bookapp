package com.ykotsiuba.bookapp.exception;

public class BookCannotBeUpdatedException extends RuntimeException {
    public BookCannotBeUpdatedException(String message) {
        super(message);
    }
}
