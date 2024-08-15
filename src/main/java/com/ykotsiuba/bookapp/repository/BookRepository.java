package com.ykotsiuba.bookapp.repository;

import com.ykotsiuba.bookapp.entity.Book;
import com.ykotsiuba.bookapp.entity.projection.BookTitleCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleAndAuthor(String title, String author);
    @Query(value = "SELECT b.* FROM books b JOIN book_member bm ON b.id = bm.book_id JOIN members m ON m.id = bm.member_id WHERE m.name = :memberName", nativeQuery = true)
    List<Book> findBooksByMemberName(@Param("memberName") String memberName);

    @Query(value = "SELECT DISTINCT b.title FROM books b JOIN book_member bm ON b.id = bm.book_id", nativeQuery = true)
    List<String> findBorrowedBooksDistinctTitles();

    @Query(value = "SELECT b.title AS title, COUNT(bm.book_id) AS count " +
            "FROM books b JOIN book_member bm ON b.id = bm.book_id " +
            "GROUP BY b.title",
            nativeQuery = true)
    List<BookTitleCountProjection> findDistinctBookTitlesWithCount();
}
