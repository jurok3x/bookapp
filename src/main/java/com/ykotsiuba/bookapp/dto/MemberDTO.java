package com.ykotsiuba.bookapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class MemberDTO {
    private Long id;
    private String name;
    private LocalDate membershipDate;
    @JsonProperty("books")
    private List<BookDTO> booksDTO = new ArrayList<>();
}
