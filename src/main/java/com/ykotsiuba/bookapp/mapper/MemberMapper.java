package com.ykotsiuba.bookapp.mapper;

import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.entity.Member;

public class MemberMapper {

    private MemberMapper() {}

    public static MemberDTO toDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setMembershipDate(member.getMembershipDate());
        if(member.getBooks() != null) {
            dto.setBooksDTO(
                    member.getBooks().stream()
                            .map(BookMapper::toDTO)
                            .toList()
            );
        }
        return dto;
    }

    public static Member toEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setMembershipDate(memberDTO.getMembershipDate());
        member.setBooks(
                memberDTO.getBooksDTO().stream()
                        .map(BookMapper::toEntity)
                        .toList()
        );
        return member;
    }
}
