package com.ykotsiuba.bookapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSaveRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
}
