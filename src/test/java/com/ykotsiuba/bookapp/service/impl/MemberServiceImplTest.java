package com.ykotsiuba.bookapp.service.impl;

import com.ykotsiuba.bookapp.dto.MemberDTO;
import com.ykotsiuba.bookapp.dto.MemberSaveRequestDTO;
import com.ykotsiuba.bookapp.entity.Book;
import com.ykotsiuba.bookapp.entity.Member;
import com.ykotsiuba.bookapp.repository.MemberRepository;
import com.ykotsiuba.bookapp.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.ykotsiuba.bookapp.util.LibraryUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberServiceImplTest {

    private MemberService memberService;
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = mock(MemberRepository.class);
        memberService = new MemberServiceImpl(memberRepository);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(memberRepository);
    }

    @Test
    void whenSave_thenReturnMember() {
        Member member = prepareMember();
        MemberSaveRequestDTO requestDTO = MemberSaveRequestDTO.builder()
                .name(member.getName())
                .build();
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        MemberDTO memberDTO = memberService.saveMember(requestDTO);

        assertNotNull(memberDTO);
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void whenFindById_thenReturnMember() {
        Member member = prepareMember();
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

        MemberDTO memberDTO = memberService.getMemberById(member.getId());

        assertNotNull(memberDTO);
        verify(memberRepository).findById(any(Long.class));
    }

    @Test
    void whenMemberNotFound_thenThrowException() {
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> memberService.getMemberById(1L));

        verify(memberRepository).findById(any(Long.class));
    }

    @Test
    void whenFindAll_thenListMembers() {
        List<Member> memberList = prepareMemberList(5);
        when(memberRepository.findAll()).thenReturn(memberList);

        List<MemberDTO> memberDTOList = memberService.getAllMembers();

        assertFalse(memberDTOList.isEmpty());
        verify(memberRepository).findAll();
    }

    @Test
    void whenDelete_thenDeleteMember() {
        Member member = prepareMember();
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));
        doNothing().when(memberRepository).delete(any(Member.class));

        memberService.deleteMember(member.getId());

        verify(memberRepository).findById(any(Long.class));
        verify(memberRepository).delete(any(Member.class));
    }

    @Test
    void whenDeleteMemberWithBook_thenThrowException() {
        Member member = prepareMember();
        Book book = prepareBook();
        member.getBooks().add(book);
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));
        doNothing().when(memberRepository).delete(any(Member.class));

        assertThrows(IllegalStateException.class, () -> memberService.deleteMember(member.getId()));

        verify(memberRepository).findById(any(Long.class));
    }

    @Test
    void whenUpdate_thenReturnMember() {
        Member member = prepareMember();
        MemberSaveRequestDTO requestDTO = MemberSaveRequestDTO.builder()
                .name(member.getName())
                .build();
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberRepository.findById(any(Long.class))).thenReturn(Optional.of(member));

        memberService.updateMember(requestDTO, member.getId());

        verify(memberRepository).findById(any(Long.class));
        verify(memberRepository).save(any(Member.class));
    }
}