package com.ykotsiuba.bookapp.dto;

import lombok.Data;

@Data
public class BookMemberRequestDTO {
    private Long bookId;
    private Long memberId;
}
