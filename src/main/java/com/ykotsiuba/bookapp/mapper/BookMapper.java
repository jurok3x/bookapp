package com.ykotsiuba.bookapp.mapper;

import com.ykotsiuba.bookapp.dto.BookDTO;
import com.ykotsiuba.bookapp.entity.Book;
import com.ykotsiuba.bookapp.entity.Member;

public class BookMapper {

    private BookMapper() {}

    public static BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setAmount(book.getAmount());
        if (book.getMembers() != null) {
            dto.setMembersIds(
                    book.getMembers().stream()
                            .map(Member::getId)
                            .toList()
            );
        }
        return dto;
    }

    public static Book toEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setAmount(bookDTO.getAmount());
        return book;
    }
}
