package com.ykotsiuba.bookapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class MemberDTO {
    private Long id;
    private String name;
    private LocalDate membershipDate;
    @JsonProperty("books")
    private Set<BookDTO> booksDTO;
}
