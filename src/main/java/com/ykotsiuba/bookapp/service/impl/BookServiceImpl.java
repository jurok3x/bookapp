package com.ykotsiuba.bookapp.service.impl;

import com.ykotsiuba.bookapp.dto.BookDTO;
import com.ykotsiuba.bookapp.dto.BookSaveRequestDTO;
import com.ykotsiuba.bookapp.entity.Book;
import com.ykotsiuba.bookapp.exception.BookCannotBeDeletedException;
import com.ykotsiuba.bookapp.exception.BookCannotBeUpdatedException;
import com.ykotsiuba.bookapp.mapper.BookMapper;
import com.ykotsiuba.bookapp.repository.BookRepository;
import com.ykotsiuba.bookapp.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ykotsiuba.bookapp.entity.enums.BookMessages.BOOK_CANNOT_BE_DELETED;
import static com.ykotsiuba.bookapp.entity.enums.BookMessages.BOOK_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookDTO saveBook(BookSaveRequestDTO requestDTO) {
        Book book = null;
        Optional<Book> optionalBook = bookRepository.findByTitleAndAuthor(requestDTO.getTitle(), requestDTO.getAuthor());
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
            book.increaseAmount();
        } else {
            Book.builder()
                    .title(requestDTO.getTitle())
                    .author(requestDTO.getAuthor())
                    .amount(1)
                    .build();
        }
        Book savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
        return BookMapper.toDTO(savedBook);
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = findOrThrow(id);
        log.info("Found book with id: {}", id);
        return BookMapper.toDTO(book);
    }

    private Book findOrThrow(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()) {
            log.error("Book not found for id: {}", id);
        }
        return optionalBook.orElseThrow(() -> new EntityNotFoundException(String.format(BOOK_NOT_FOUND.getMessage(), id)));
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteBook(Long id) {
        Book book = findOrThrow(id);
        if(!book.getMembers().isEmpty()) {
            throw new BookCannotBeDeletedException(BOOK_CANNOT_BE_DELETED.getMessage());
        }
        log.info("Deleted book with id: {}", id);
        bookRepository.delete(book);
    }

    @Override
    public BookDTO updateBook(BookSaveRequestDTO requestDTO, Long id) {
        Book book = findOrThrow(id);
        Optional<Book> optionalBook = bookRepository.findByTitleAndAuthor(requestDTO.getTitle(), requestDTO.getAuthor());
        if(optionalBook.isPresent()) {
            throw new BookCannotBeUpdatedException(BOOK_CANNOT_BE_DELETED.getMessage());
        }
        log.info("Updating book with id: {}", id);
        book.setTitle(requestDTO.getTitle());
        book.setAuthor(requestDTO.getAuthor());
        Book updatedBook = bookRepository.save(book);
        return BookMapper.toDTO(updatedBook);
    }
}
