package com.ykotsiuba.bookapp.service.impl;

import com.ykotsiuba.bookapp.dto.BookMemberRequestDTO;
import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.entity.Book;
import com.ykotsiuba.bookapp.entity.Member;
import com.ykotsiuba.bookapp.mapper.MemberMapper;
import com.ykotsiuba.bookapp.repository.BookRepository;
import com.ykotsiuba.bookapp.repository.MemberRepository;
import com.ykotsiuba.bookapp.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    @Value("${max_books_number}")
    private int maxBooksNumber;

    @Override
    public MemberDTO borrowBook(BookMemberRequestDTO requestDTO) {
        Member member = memberRepository.findById(requestDTO.getMemberId()).get();
        Book book = bookRepository.findById(requestDTO.getBookId()).get();

        if (book.getAmount() <= 0) {
            throw new IllegalStateException("Book is not available for borrowing.");
        }

        if (member.getBooks().size() >= maxBooksNumber) {
            throw new IllegalStateException("Member has reached the maximum number of borrowed books.");
        }

        book.decreaseAmount();
        book.getMembers().add(member);
        member.getBooks().add(book);

        bookRepository.save(book);
        Member updatedMember = memberRepository.save(member);

        log.info("Member {} borrowed book {}", member.getId(), book.getId());

        return MemberMapper.toDTO(updatedMember);
    }

    @Override
    public MemberDTO returnBook(BookMemberRequestDTO requestDTO) {
        Member member = memberRepository.findById(requestDTO.getMemberId()).get();
        Book book = bookRepository.findById(requestDTO.getBookId()).get();

        if (!member.getBooks().contains(book)) {
            throw new IllegalStateException("This book was not borrowed by the member.");
        }

        book.increaseAmount();
        book.getMembers().remove(member);
        member.getBooks().remove(book);

        bookRepository.save(book);
        Member updatedMember = memberRepository.save(member);

        log.info("Member {} returned book {}", member.getId(), book.getId());

        return MemberMapper.toDTO(updatedMember);
    }
}
