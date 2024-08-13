package com.ykotsiuba.bookapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Set;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private int amount;
    @JsonProperty("members")
    private Set<MemberDTO> membersDTO;
}
