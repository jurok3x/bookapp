package com.ykotsiuba.bookapp.util;

import com.github.javafaker.Faker;
import com.ykotsiuba.bookapp.dto.BookDTO;
import com.ykotsiuba.bookapp.dto.BookSaveRequestDTO;
import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.entity.Book;
import com.ykotsiuba.bookapp.entity.Member;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.List;

public class LibraryUtil {

    private LibraryUtil() {};

    private static Faker FAKER = new Faker();

    public static Book prepareBook() {
        return Book.builder()
                .id(3L)
                .title(FAKER.book().title())
                .author(FAKER.book().author())
                .amount(FAKER.number().numberBetween(1, 10))
                .members(Lists.newArrayList())
                .build();
    }

    public static List<Book> prepareBookList(int size) {
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Book book = prepareBook();
            bookList.add(book);
        }
        return bookList;
    }

    public static BookDTO prepareBookDTO() {
        return BookDTO.builder()
                .id(3L)
                .title(FAKER.book().title())
                .author(FAKER.book().author())
                .amount(FAKER.number().numberBetween(1, 10))
                .build();
    }

    public static Member prepareMember() {
        return Member.builder()
                .id(3L)
                .name(FAKER.harryPotter().character())
                .books(Lists.newArrayList())
                .build();
    }
    public static List<Member> prepareMemberList(int size) {
        List<Member> memberList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Member member = prepareMember();
            memberList.add(member);
        }
        return memberList;
    }


    public static MemberDTO prepareMemberDTO() {
        return MemberDTO.builder()
                .id(3L)
                .name(FAKER.harryPotter().character())
                .build();
    }

    public static BookSaveRequestDTO prepareSaveBookDTO() {
        return BookSaveRequestDTO.builder()
                .title(FAKER.book().title())
                .author(FAKER.book().author())
                .build();
    }

//    public static MemberSaveRequestDTO prepareMemberBookDTO() {
//        return MemberSaveRequestDTO.builder()
//                .name()
//                .build();
//    }
//
//    public static BookMemberRequestDTO prepareBookMemberDTO() {
//        return BookMemberRequestDTO.builder()
//                .bookId()
//                .memberId()
//                .build();
//    }
}
