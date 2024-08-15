package com.ykotsiuba.bookapp.service;

import com.ykotsiuba.bookapp.dto.BookDTO;
import com.ykotsiuba.bookapp.dto.BookMemberRequestDTO;
import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.entity.projection.BookTitleCountProjection;

import java.util.List;

public interface LibraryService {
    /**
     * Allows a member to borrow a book if it is available and if the member has not reached
     * the maximum number of borrowed books. Updates both the book's and the member's records.
     *
     * @param requestDTO the data transfer object containing member and book IDs.
     * @return a {@link MemberDTO} representing the updated member after borrowing the book.
     * @throws IllegalStateException if the book is not available for borrowing or if the member has reached
     *                                the maximum number of borrowed books.
     */
    MemberDTO borrowBook(BookMemberRequestDTO requestDTO);

    /**
     * Allows a member to return a book. Updates both the book's and the member's records.
     *
     * @param requestDTO the data transfer object containing member and book IDs.
     * @return a {@link MemberDTO} representing the updated member after returning the book.
     * @throws IllegalStateException if the book was not borrowed by the member.
     */
    MemberDTO returnBook(BookMemberRequestDTO requestDTO);

    /**
     * Retrieves a list of books borrowed by a specific member identified by their name.
     *
     * @param name the name of the member whose borrowed books are to be retrieved.
     * @return a {@link List} of {@link BookDTO} representing books borrowed by the member.
     */
    List<BookDTO> findBooksByMemberName(String name);

    /**
     * Retrieves distinct titles of books that have been borrowed.
     *
     * @return a {@link List} of distinct book titles that have been borrowed.
     */
    List<String> findBorrowedBooksDistinctTitles();

    /**
     * Retrieves distinct titles of borrowed books along with the count of how many copies of each book
     * have been borrowed.
     *
     * @return a {@link List} of {@link BookTitleCountProjection} objects, each representing a distinct
     *         book title and the count of borrowed copies.
     */
    List<BookTitleCountProjection> findDistinctBookTitlesWithCount();
}
