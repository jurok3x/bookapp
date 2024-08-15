package com.ykotsiuba.bookapp.controller;

import com.ykotsiuba.bookapp.dto.BookDTO;
import com.ykotsiuba.bookapp.dto.BookMemberRequestDTO;
import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.entity.projection.BookTitleCountProjection;
import com.ykotsiuba.bookapp.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/borrowed_member")
    public ResponseEntity<List<BookDTO>> findBooksByMemberName(@RequestParam String name) {
        List<BookDTO> books = libraryService.findBooksByMemberName(name);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/borrowed_titles")
    public ResponseEntity<List<String>> findBorrowedBooksDistinctTitles() {
        List<String> booksTitles = libraryService.findBorrowedBooksDistinctTitles();
        return ResponseEntity.ok(booksTitles);
    }

    @GetMapping("/borrowed_count")
    public ResponseEntity<List<BookTitleCountProjection>> findDistinctBookTitlesWithCount() {
        List<BookTitleCountProjection> booksTitlesCount = libraryService.findDistinctBookTitlesWithCount();
        return ResponseEntity.ok(booksTitlesCount);
    }
}
