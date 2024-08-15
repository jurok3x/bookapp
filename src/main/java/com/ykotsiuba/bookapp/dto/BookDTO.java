package com.ykotsiuba.bookapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private int amount;
    @JsonProperty("members")
    private List<Long> membersIds = new ArrayList<>();
}
