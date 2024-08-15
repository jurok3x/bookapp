package com.ykotsiuba.bookapp.service;

import com.ykotsiuba.bookapp.dto.BookDTO;
import com.ykotsiuba.bookapp.dto.BookMemberRequestDTO;
import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.entity.projection.BookTitleCountProjection;

import java.util.List;

public interface LibraryService {
    MemberDTO borrowBook(BookMemberRequestDTO requestDTO);
    MemberDTO returnBook(BookMemberRequestDTO requestDTO);
    List<BookDTO> findBooksByMemberName(String name);
    List<String> findBorrowedBooksDistinctTitles();
    List<BookTitleCountProjection> findDistinctBookTitlesWithCount();
}
