package com.ykotsiuba.bookapp.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberMessages {

    MEMBER_DELETED("Member deleted successfully."),
    MEMBER_NOT_FOUND("Member not found for ID: %d"),
    MEMBER_CANNOT_BE_DELETED("A member cannot be deleted if it is borrowed by at least one book.");

    private final String message;
}
