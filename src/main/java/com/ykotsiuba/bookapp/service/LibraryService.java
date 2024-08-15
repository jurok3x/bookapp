package com.ykotsiuba.bookapp.service;

import com.ykotsiuba.bookapp.dto.BookMemberRequestDTO;
import com.ykotsiuba.bookapp.dto.MemberDTO;

public interface LibraryService {
    MemberDTO borrowBook(BookMemberRequestDTO requestDTO);
    MemberDTO returnBook(BookMemberRequestDTO requestDTO);
}
