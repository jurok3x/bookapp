package com.ykotsiuba.bookapp.service.impl;

import com.ykotsiuba.bookapp.dto.BookDTO;
import com.ykotsiuba.bookapp.dto.BookMemberRequestDTO;
import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.entity.Book;
import com.ykotsiuba.bookapp.entity.Member;
import com.ykotsiuba.bookapp.entity.projection.BookTitleCountProjection;
import com.ykotsiuba.bookapp.repository.BookRepository;
import com.ykotsiuba.bookapp.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.ykotsiuba.bookapp.util.LibraryUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LibraryServiceImplTest {

    private LibraryServiceImpl libraryServiceImpl;
    private MemberRepository memberRepository;
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        memberRepository = mock(MemberRepository.class);
        libraryServiceImpl = new LibraryServiceImpl(bookRepository, memberRepository);
        ReflectionTestUtils.setField(libraryServiceImpl, "maxBooksNumber", 10);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(memberRepository);
    }

    @Test
    void whenBorrowBook_thenReturnAuthor() {
        Book book = prepareBook();
        Member member = prepareMember();
        BookMemberRequestDTO requestDTO = BookMemberRequestDTO
                .builder()
                .bookId(book.getId())
                .memberId(member.getId())
                .build();
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        MemberDTO memberDTO = libraryServiceImpl.borrowBook(requestDTO);

        assertNotNull(memberDTO);
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(bookRepository, times(1)).save(bookCaptor.capture());
        verify(memberRepository, times(1)).save(memberCaptor.capture());
        verify(bookRepository).findById(any(Long.class));
        verify(memberRepository).findById(any(Long.class));
        Book capturedBook = bookCaptor.getValue();
        Member capturedMember = memberCaptor.getValue();
        assertEquals(book.getAmount(), capturedBook.getAmount());
        assertFalse(capturedBook.getMembers().isEmpty());
        assertFalse(capturedMember.getBooks().isEmpty());
    }

    @Test
    void whenMemberHasToManyBooks_thenTrowsException() {
        Book book = prepareBook();
        List<Book> books = prepareBookList(10);
        Member member = prepareMember();
        member.setBooks(books);
        BookMemberRequestDTO requestDTO = BookMemberRequestDTO
                .builder()
                .bookId(book.getId())
                .memberId(member.getId())
                .build();
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

        assertThrows(IllegalStateException.class, () -> libraryServiceImpl.borrowBook(requestDTO));
        verify(bookRepository).findById(any(Long.class));
        verify(memberRepository).findById(any(Long.class));
    }

    @Test
    void whenBookAmountIsZero_thenTrowsException() {
        Book book = prepareBook();
        book.setAmount(0);
        Member member = prepareMember();
        BookMemberRequestDTO requestDTO = BookMemberRequestDTO
                .builder()
                .bookId(book.getId())
                .memberId(member.getId())
                .build();
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

        assertThrows(IllegalStateException.class, () -> libraryServiceImpl.borrowBook(requestDTO));
        verify(bookRepository).findById(any(Long.class));
        verify(memberRepository).findById(any(Long.class));
    }

    @Test
    void whenReturnBook_thenReturnAuthor() {
        List<Book> books = prepareBookList(5);
        Book book = books.get(0);
        Member member = prepareMember();
        member.setBooks(books);
        BookMemberRequestDTO requestDTO = BookMemberRequestDTO
                .builder()
                .bookId(book.getId())
                .memberId(member.getId())
                .build();
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        MemberDTO memberDTO = libraryServiceImpl.returnBook(requestDTO);

        assertNotNull(memberDTO);
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(bookRepository, times(1)).save(bookCaptor.capture());
        verify(memberRepository, times(1)).save(memberCaptor.capture());
        verify(bookRepository).findById(any(Long.class));
        verify(memberRepository).findById(any(Long.class));
        Book capturedBook = bookCaptor.getValue();
        Member capturedMember = memberCaptor.getValue();
        assertEquals(book.getAmount(), capturedBook.getAmount());
        assertFalse(capturedBook.getMembers().contains(member));
        assertFalse(capturedMember.getBooks().contains(book));
    }

    @Test
    void whenReturnWrongBook_thenThrowsException() {
        List<Book> books = prepareBookList(5);
        Book book = books.get(0);
        Member member = prepareMember();
        books.remove(0);
        member.setBooks(books);
        BookMemberRequestDTO requestDTO = BookMemberRequestDTO
                .builder()
                .bookId(book.getId())
                .memberId(member.getId())
                .build();
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

        assertThrows(IllegalStateException.class, () -> libraryServiceImpl.returnBook(requestDTO));

        verify(bookRepository).findById(any(Long.class));
        verify(memberRepository).findById(any(Long.class));
    }

    @Test
    void whenFindBooksByMemberName_thenReturnBooks() {
        List<Book> books = prepareBookList(5);
        when(bookRepository.findBooksByMemberName(anyString())).thenReturn(books);

        List<BookDTO> booksByMemberName = libraryServiceImpl.findBooksByMemberName("John Doe");

        assertFalse(booksByMemberName.isEmpty());
        verify(bookRepository).findBooksByMemberName(any(String.class));
    }

    @Test
    void whenFindBorrowedBooksDistinctTitles_thenReturnTitles() {
        List<Book> books = prepareBookList(5);
        List<String> titles = books.stream()
                .map(Book::getTitle)
                .toList();
        when(bookRepository.findBorrowedBooksDistinctTitles()).thenReturn(titles);

        List<String> booksByMemberName = libraryServiceImpl.findBorrowedBooksDistinctTitles();

        assertFalse(booksByMemberName.isEmpty());
        verify(bookRepository).findBorrowedBooksDistinctTitles();
    }

    @Test
    void whenFindBorrowedBooksDistinctTitlesCount_thenReturnCount() {
        BookTitleCountProjection bookCount = mock(BookTitleCountProjection.class);
        when(bookCount.getTitle()).thenReturn("Book One");
        when(bookCount.getCount()).thenReturn(5);
        List<BookTitleCountProjection> response = Arrays.asList(bookCount);
        when(bookRepository.findDistinctBookTitlesWithCount()).thenReturn(response);

        List<BookTitleCountProjection> bookTitlesWithCount = libraryServiceImpl.findDistinctBookTitlesWithCount();

        assertFalse(bookTitlesWithCount.isEmpty());
        verify(bookRepository).findDistinctBookTitlesWithCount();
    }
}