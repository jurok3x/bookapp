package com.ykotsiuba.bookapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookMemberRequestDTO {
    private Long bookId;
    private Long memberId;
}
