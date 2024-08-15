package com.ykotsiuba.bookapp.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BookMessages {

    BOOK_DELETED("Book deleted successfully."),
    BOOK_NOT_FOUND("Book not found for ID: %d"),
    BOOK_CANNOT_BE_DELETED("A book cannot be deleted if it is borrowed by at least one member."),
    BOOK_CANNOT_BE_UPDATED("A book cannot be updated if another book with same title and author already exists.");

    private final String message;
}
