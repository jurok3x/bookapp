package com.ykotsiuba.bookapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberSaveRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
}
