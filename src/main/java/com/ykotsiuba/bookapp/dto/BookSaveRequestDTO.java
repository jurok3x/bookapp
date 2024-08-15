package com.ykotsiuba.bookapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookSaveRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters long")
    @Pattern(regexp = "^[A-Z].*", message = "Title must start with a capital letter")
    private String title;

    @NotBlank(message = "Author is required")
    @Pattern(regexp = "^[A-Z][a-z]+\\s[A-Z][a-z]+$", message = "Author must be in the format 'First Last', with both names capitalized")
    private String author;
}
