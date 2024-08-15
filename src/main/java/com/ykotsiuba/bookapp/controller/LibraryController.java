package com.ykotsiuba.bookapp.controller;

import com.ykotsiuba.bookapp.dto.BookMemberRequestDTO;
import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping("/borrow")
    public ResponseEntity<MemberDTO> borrowBook(@RequestBody BookMemberRequestDTO requestDTO) {
        MemberDTO member = libraryService.borrowBook(requestDTO);
        return ResponseEntity.ok(member);
    }

    @PostMapping("/return")
    public ResponseEntity<MemberDTO> returnBook(@RequestBody BookMemberRequestDTO requestDTO) {
        MemberDTO member = libraryService.returnBook(requestDTO);
        return ResponseEntity.ok(member);
    }
}
