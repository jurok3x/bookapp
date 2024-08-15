package com.ykotsiuba.bookapp.service.impl;

import com.ykotsiuba.bookapp.dto.BookDTO;
import com.ykotsiuba.bookapp.dto.BookSaveRequestDTO;
import com.ykotsiuba.bookapp.entity.Book;
import com.ykotsiuba.bookapp.entity.Member;
import com.ykotsiuba.bookapp.repository.BookRepository;
import com.ykotsiuba.bookapp.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static com.ykotsiuba.bookapp.util.LibraryUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    private BookService bookService;
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookServiceImpl(bookRepository);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void whenSave_thenReturnBook() {
        Book book = prepareBook();
        BookSaveRequestDTO requestDTO = BookSaveRequestDTO.builder()
                        .author(book.getAuthor())
                        .title(book.getTitle())
                        .build();
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookRepository.findByTitleAndAuthor(any(String.class), any(String.class))).thenReturn(Optional.empty());

        BookDTO savedBook = bookService.saveBook(requestDTO);

        assertNotNull(savedBook);
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository, times(1)).save(bookCaptor.capture());
        verify(bookRepository).findByTitleAndAuthor(any(String.class), any(String.class));

        Book capturedBook = bookCaptor.getValue();
        assertEquals(1, capturedBook.getAmount());
    }

    @Test
    void whenSaveExsisting_thenIncreaseNumber() {
        Book book = prepareBook();
        BookSaveRequestDTO requestDTO = BookSaveRequestDTO.builder()
                .author(book.getAuthor())
                .title(book.getTitle())
                .build();
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookRepository.findByTitleAndAuthor(any(String.class), any(String.class))).thenReturn(Optional.of(book));

        BookDTO savedBook = bookService.saveBook(requestDTO);

        assertNotNull(savedBook);
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository, times(1)).save(bookCaptor.capture());
        verify(bookRepository).findByTitleAndAuthor(any(String.class), any(String.class));

        Book capturedBook = bookCaptor.getValue();
        assertEquals(book.getAmount(), capturedBook.getAmount());
    }

    @Test
    void whenFindById_thenReturnBook() {
        Book book = prepareBook();
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        BookDTO bookDTO = bookService.getBookById(book.getId());

        assertNotNull(bookDTO);
        verify(bookRepository).findById(any(Long.class));
    }

    @Test
    void whenFindBookNotFound_thenThrowException() {
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository).findById(any(Long.class));
    }

    @Test
    void whenFindAll_thenReturnBookList() {
        List<Book> books = prepareBookList(5);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> bookDTOS = bookService.getAllBooks();

        assertFalse(bookDTOS.isEmpty());
        verify(bookRepository).findAll();
    }

    @Test
    void whenDelete_thenDeleteBook() {
        Book book = prepareBook();
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(any(Book.class));

        bookService.deleteBook(book.getId());

        verify(bookRepository).delete(any(Book.class));
        verify(bookRepository).findById(any(Long.class));
    }

    @Test
    void whenDeleteError_thenThrowException() {
        Book book = prepareBook();
        Member member =prepareMember();
        book.getMembers().add(member);
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));

        assertThrows(IllegalStateException.class, () -> bookService.deleteBook(book.getId()));

        verify(bookRepository).findById(any(Long.class));
    }

    @Test
    void whenUpdate_thenReturnBook() {
        Book book = prepareBook();
        BookSaveRequestDTO requestDTO = BookSaveRequestDTO.builder()
                .author(book.getAuthor())
                .title(book.getTitle())
                .build();
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookRepository.findByTitleAndAuthor(any(String.class), any(String.class))).thenReturn(Optional.empty());

        BookDTO updatedBook = bookService.updateBook(requestDTO, book.getId());

        assertNotNull(updatedBook);
        verify(bookRepository).findById(any(Long.class));
        verify(bookRepository).save(any(Book.class));
        verify(bookRepository).findByTitleAndAuthor(any(String.class), any(String.class));
    }

    @Test
    void whenUpdatedBookExists_thenThrowException() {
        Book book = prepareBook();
        BookSaveRequestDTO requestDTO = BookSaveRequestDTO.builder()
                .author(book.getAuthor())
                .title(book.getTitle())
                .build();
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookRepository.findByTitleAndAuthor(any(String.class), any(String.class))).thenReturn(Optional.of(book));

        assertThrows(IllegalStateException.class, () -> bookService.updateBook(requestDTO, book.getId()));

        verify(bookRepository).findById(any(Long.class));
        verify(bookRepository).findByTitleAndAuthor(any(String.class), any(String.class));
    }
}