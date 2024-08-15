package com.ykotsiuba.bookapp.service;

import com.ykotsiuba.bookapp.dto.BookDTO;
import com.ykotsiuba.bookapp.dto.BookSaveRequestDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface BookService {
    /**
     * Saves a new book or updates an existing book based on the provided request data.
     * If a book with the same title and author already exists, its amount is increased.
     * Otherwise, a new book is created and saved.
     *
     * @param requestDTO the data transfer object containing book details to be saved.
     * @return a {@link BookDTO} representing the saved or updated book.
     */
    BookDTO saveBook(BookSaveRequestDTO book);

    /**
     * Retrieves a book by its unique identifier.
     *
     * @param id the unique identifier of the book.
     * @return a {@link BookDTO} representing the found book.
     * @throws EntityNotFoundException if no book is found with the specified id.
     */
    BookDTO getBookById(Long id);

    /**
     * Retrieves all books in the system.
     *
     * @return a {@link List} of {@link BookDTO} objects representing all books.
     */
    List<BookDTO> getAllBooks();

    /**
     * Deletes a book by its unique identifier.
     * If the book is associated with any members, it cannot be deleted.
     *
     * @param id the unique identifier of the book to be deleted.
     * @throws IllegalStateException if the book has associated members and cannot be deleted.
     */
    void deleteBook(Long id);

    /**
     * Updates the details of an existing book.
     * The book is identified by its unique identifier, and its title and author are updated
     * based on the provided request data. If a book with the same title and author already exists,
     * an exception is thrown.
     *
     * @param requestDTO the data transfer object containing updated book details.
     * @param id the unique identifier of the book to be updated.
     * @return a {@link BookDTO} representing the updated book.
     * @throws IllegalStateException if a book with the same title and author already exists.
     */
    BookDTO updateBook(BookSaveRequestDTO requestDTO, Long id);
}
