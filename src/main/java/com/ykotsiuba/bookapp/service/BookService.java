package com.ykotsiuba.bookapp.service;

import com.ykotsiuba.bookapp.dto.BookDTO;
import com.ykotsiuba.bookapp.dto.BookSaveRequestDTO;

import java.util.List;

public interface BookService {
    BookDTO saveBook(BookSaveRequestDTO book);
    BookDTO getBookById(Long id);
    List<BookDTO> getAllBooks();
    void deleteBook(Long id);
    BookDTO updateBook(BookSaveRequestDTO book, Long id);
}
