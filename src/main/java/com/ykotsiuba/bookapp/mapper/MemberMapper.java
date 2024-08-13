package com.ykotsiuba.bookapp.mapper;

import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.entity.Book;
import com.ykotsiuba.bookapp.entity.Member;

import java.util.stream.Collectors;

public class MemberMapper {

    private MemberMapper() {}

    public static MemberDTO toDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setMembershipDate(member.getMembershipDate());
        dto.setBooksDTO(
                member.getBooks().stream()
                        .map(BookMapper::toDTO)
                        .collect(Collectors.toSet())
        );
        return dto;
    }

    public static Member toEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setBooks(
                memberDTO.getBooksDTO().stream()
                        .map(BookMapper::toEntity)
                        .collect(Collectors.toSet())
        );
        return member;
    }
}
