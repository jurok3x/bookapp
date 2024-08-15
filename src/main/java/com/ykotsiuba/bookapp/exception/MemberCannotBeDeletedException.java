package com.ykotsiuba.bookapp.exception;

public class MemberCannotBeDeletedException extends RuntimeException {
    public MemberCannotBeDeletedException(String message) {
        super(message);
    }
}
