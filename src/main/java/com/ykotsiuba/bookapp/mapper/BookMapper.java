package com.ykotsiuba.bookapp.mapper;

import com.ykotsiuba.bookapp.dto.BookDTO;
import com.ykotsiuba.bookapp.entity.Book;

import java.util.stream.Collectors;

public class BookMapper {

    private BookMapper() {}

    public static BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setAmount(book.getAmount());
        dto.setMembersDTO(
                book.getMembers().stream()
                        .map(MemberMapper::toDTO)
                        .collect(Collectors.toSet())
        );
        return dto;
    }

    public static Book toEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setAmount(bookDTO.getAmount());
        book.setMembers(
                bookDTO.getMembersDTO().stream()
                        .map(MemberMapper::toEntity)
                        .collect(Collectors.toSet()));
        return book;
    }
}
